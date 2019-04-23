import {closest, contains} from 'metal-dom';
import Component from 'metal-component';
import {Config} from 'metal-state';
import {isFunction, isObject} from 'metal';
import Soy from 'metal-soy';

import FragmentEditableField from './FragmentEditableField.es';
import FragmentStyleEditor from './FragmentStyleEditor.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdateOnChangeProperties} from '../../utils/FragmentsEditorComponentUtils.es';
import {prefixSegmentsExperienceId} from '../../utils/prefixSegmentsExperienceId.es';
import templates from './FragmentEntryLinkContent.soy';
import {UPDATE_EDITABLE_VALUE} from '../../actions/actions.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';

const EDITABLE_FRAGMENT_ENTRY_PROCESSOR = 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

/**
 * Creates a Fragment Entry Link Content component.
 * @review
 */
class FragmentEntryLinkContent extends Component {

	/**
	 * @inheritDoc
	 */
	created() {
		this._handleOpenStyleTooltip = this._handleOpenStyleTooltip.bind(this);
		this._handleStyleChanged = this._handleStyleChanged.bind(this);
	}

	/**
	 * @inheritDoc
	 */
	disposed() {
		this._destroyEditables();
	}

	/**
	 * @inheritDoc
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (state.languageId && Liferay.Language.direction) {
			nextState = setIn(
				nextState,
				['_languageDirection'],
				Liferay.Language.direction[state.languageId] || 'ltr'
			);
		}

		nextState = setIn(
			nextState,
			['content'],
			this.content ? Soy.toIncDom(this.content) : null
		);

		return nextState;
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		if (this.content) {
			this._renderContent(this.content);
		}
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdateOnChangeProperties(
			changes,
			[
				'content',
				'languageId',
				'segmentsExperienceId',
				'selectedMappingTypes',
				'showMapping'
			]
		);
	}

	/**
	 * Renders the content if it is changed.
	 * @inheritDoc
	 * @param {string} newContent The new content to render.
	 */
	syncContent(newContent) {
		if (newContent && (newContent !== this.content)) {
			this._renderContent(newContent);
		}
	}

	/**
	 * Handles changes to editable values.
	 * @inheritDoc
	 * @param {object} newEditableValues The updated values.
	 * @param {object} oldEditableValues The original values.
	 */
	syncEditableValues(newEditableValues, oldEditableValues) {
		if (newEditableValues !== oldEditableValues) {
			if (this._editables) {
				this._editables.forEach(
					editable => {
						const editableValues = (
							newEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR] &&
							newEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.editableId]
						) ?
							newEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.editableId] :
							{
								defaultValue: editable.content
							};

						editable.editableValues = editableValues;
					}
				);
			}

			this._update(
				{
					defaultLanguageId: this.defaultLanguageId,
					defaultSegmentsExperienceId: this.defaultSegmentsExperienceId,
					languageId: this.languageId,
					segmentsExperienceId: this.segmentsExperienceId,
					updateFunctions: []
				}
			);
		}
	}

	/**
	 * Propagates the store to editable fields when it's loaded.
	 */
	syncStore() {
		if (this._editables) {
			this._editables.forEach(
				editable => {
					editable.store = this.store;
				}
			);
		}
	}

	/**
	 * Create instances of a Fragment Style Editor for each element styled with
	 * a background image.
	 */
	_createBackgroundImageStyleEditors() {
		if (this._backgroundImageStyleEditors) {
			this._backgroundImageStyleEditors.forEach(
				styleEditor => styleEditor.dispose()
			);
		}

		this._backgroundImageStyleEditors = this._styles
			.filter(
				style => style.css && style.css.backgroundImage
			)
			.map(
				style => style.nodes.map(
					node => new FragmentStyleEditor(
						{
							cssText: style.cssText,
							editorsOptions: {
								imageSelectorURL: this.imageSelectorURL
							},
							fragmentEntryLinkId: this.fragmentEntryLinkId,
							node,
							portletNamespace: this.portletNamespace,
							selectorText: style.selectorText,
							showMapping: this.showMapping,
							store: this.store,
							type: 'backgroundImage'
						}
					)
				)
			)
			.reduce(
				(styleEditorsA, styleEditorsB) => [
					...styleEditorsA,
					...styleEditorsB
				],
				[]
			);

		this._backgroundImageStyleEditors.forEach(
			styleEditor => {
				styleEditor.on('openTooltip', this._handleOpenStyleTooltip);
				styleEditor.on('styleChanged', this._handleStyleChanged);
			}
		);
	}

	/**
	 * Creates instances of a fragment editable field for each editable.
	 */
	_createEditables() {
		this._destroyEditables();

		this._editables = [...this.refs.content.querySelectorAll('lfr-editable')].map(
			editable => {
				const editableValues = (
					this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR] &&
					this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.id]
				) ? this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.id] :
					{
						defaultValue: editable.innerHTML
					};

				const defaultEditorConfiguration = this
					.defaultEditorConfigurations[editable.getAttribute('type')] ||
					this.defaultEditorConfigurations.text;

				return new FragmentEditableField(
					{
						content: editable.innerHTML,
						editableId: editable.id,
						editableValues,
						element: editable,
						fragmentEntryLinkId: this.fragmentEntryLinkId,

						processorsOptions: {
							defaultEditorConfiguration,
							imageSelectorURL: this.imageSelectorURL
						},

						segmentsExperienceId: this.segmentsExperienceId,
						showMapping: this.showMapping,
						store: this.store,
						type: editable.getAttribute('type')
					}
				);
			}
		);
	}

	_createStyles() {
		const elements = [];

		for (let styleIndex = 0; styleIndex < document.styleSheets.length; styleIndex += 1) {
			const cssStyle = document.styleSheets[styleIndex];

			if (contains(this.refs.content, cssStyle.ownerNode) && cssStyle.rules) {
				for (let ruleIndex = 0; ruleIndex < cssStyle.rules.length; ruleIndex += 1) {
					const cssRule = cssStyle.rules[ruleIndex];

					elements.push(
						{
							css: cssRule.style,
							cssText: cssRule.cssText,
							nodes: [
								...this.refs.content.querySelectorAll(
									cssRule.selectorText
								)
							],
							selectorText: cssRule.selectorText
						}
					);
				}
			}
		}

		this._styles = elements;
	}

	/**
	 * Destroys existing fragment editable field instances.
	 */
	_destroyEditables() {
		if (this._editables) {
			this._editables.forEach(
				editable => editable.dispose()
			);

			this._editables = [];
		}
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentEntryLinkContentClick(event) {
		const element = event.srcElement;

		if (closest(element, '[href]') &&
			!('lfrPageEditorHrefEnabled' in element.dataset)) {

			event.preventDefault();
		}
	}

	/**
	 * Callback executed when a tooltip is opened.
	 */
	_handleOpenStyleTooltip() {
		if (!this._backgroundImageStyleEditors) {
			return;
		}

		this._backgroundImageStyleEditors.forEach(
			styleEditor => styleEditor.disposeStyleTooltip()
		);
	}

	/**
	 * @param {Object} event
	 */
	_handleStyleChanged(event) {
		const editableValueSegmentsExperienceId = prefixSegmentsExperienceId(this.segmentsExperienceId) ||
			prefixSegmentsExperienceId(this.defaultSegmentsExperienceId);

		this.store.dispatchAction(
			UPDATE_EDITABLE_VALUE,
			{
				editableId: event.name,
				editableValue: event.value,
				editableValueId: this.languageId,
				editableValueSegmentsExperienceId,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			}
		);
	}

	/**
	 * Parses and renders the fragment entry link content with AUI.
	 * @param {string} content
	 * @private
	 */
	_renderContent(content) {
		if (content && this.refs.content) {
			AUI().use(
				'aui-parse-content',
				A => {
					const contentNode = A.one(this.refs.content);
					contentNode.plug(A.Plugin.ParseContent);
					contentNode.setContent(content);

					this._createStyles();

					this._createEditables();

					this._createBackgroundImageStyleEditors();

					this._update(
						{
							defaultLanguageId: this.defaultLanguageId,
							defaultSegmentsExperienceId: this.defaultSegmentsExperienceId,
							languageId: this.languageId,
							segmentsExperienceId: this.segmentsExperienceId,
							updateFunctions: []
						}
					);
				}
			);
		}
	}

	/**
	 * Runs a set of update functions through the editable values inside this
	 * fragment entry link.
	 * @param {string} languageId The current language ID.
	 * @param {string} defaultLanguageId The default language ID.
	 * @param {Array<Function>} updateFunctions The set of update functions to
	 * execute for each editable value.
	 * @private
	 */
	_update(
		{
			defaultLanguageId,
			defaultSegmentsExperienceId,
			languageId,
			segmentsExperienceId,
			updateFunctions
		}
	) {
		const editableValues = this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

		Object.keys(editableValues).forEach(
			editableId => {
				const editableValue = editableValues[editableId];
				const segmentedEditableValue = segmentsExperienceId && editableValue[segmentsExperienceId] || editableValue[defaultSegmentsExperienceId];

				const defaultSegmentedEditableValue = editableValue[defaultSegmentsExperienceId];

				const defaultValue = (segmentedEditableValue && segmentedEditableValue[defaultLanguageId]) ||
					defaultSegmentedEditableValue && defaultSegmentedEditableValue[defaultLanguageId] ||
					editableValue.defaultValue;
				const mappedField = editableValue.mappedField || '';
				const value = segmentedEditableValue && segmentedEditableValue[languageId];

				updateFunctions.forEach(
					updateFunction => updateFunction(
						editableId,
						value,
						defaultValue,
						mappedField
					)
				);
			}
		);
	}

}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEntryLinkContent.STATE = {

	/**
	 * Fragment content to be rendered.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {string}
	 */
	content: Config.any()
		.setter(
			content => {
				return !isFunction(content) && isObject(content) ? content.value.content : content;
			}
		)
		.value(''),

	/**
	 * Editable values that should be used instead of the default ones inside
	 * editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {!Object}
	 */
	editableValues: Config.object().required(),

	/**
	 * Fragment entry link ID.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkContent
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * If <code>true</code>, the asset mapping is enabled.
	 * @default false
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {boolean}
	 */
	showMapping: Config.bool().value(false)
};

const ConnectedFragmentEntryLinkContent = getConnectedComponent(
	FragmentEntryLinkContent,
	[
		'defaultEditorConfigurations',
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'imageSelectorURL',
		'languageId',
		'portletNamespace',
		'selectedMappingTypes',
		'segmentsExperienceId',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkContent, templates);

export {
	ConnectedFragmentEntryLinkContent,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FragmentEntryLinkContent
};

export default ConnectedFragmentEntryLinkContent;