import {addClasses, contains, removeClasses} from 'metal-dom';
import Component from 'metal-component';
import {Config} from 'metal-state';
import {isFunction, isObject} from 'metal';
import Soy from 'metal-soy';

import FragmentEditableField from './FragmentEditableField.es';
import FragmentStyleEditor from './FragmentStyleEditor.es';
import MetalStore from '../../store/store.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdateOnChangeProperties} from '../../utils/FragmentsEditorComponentUtils.es';
import templates from './FragmentEntryLinkContent.soy';
import {UPDATE_EDITABLE_VALUE} from '../../actions/actions.es';

const EDITABLE_FRAGMENT_ENTRY_PROCESSOR = 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

/**
 * FragmentEntryLinkContent
 * @review
 */
class FragmentEntryLinkContent extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleOpenStyleTooltip = this._handleOpenStyleTooltip.bind(this);
		this._handleStyleChanged = this._handleStyleChanged.bind(this);
		this._updateEditableStatus = this._updateEditableStatus.bind(this);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._destroyEditables();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		return setIn(
			state,
			['content'],
			this.content ? Soy.toIncDom(this.content) : null
		);
	}

	/**
	 * @inheritDoc
	 * @review
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
				'selectedMappingTypes',
				'showMapping'
			]
		);
	}

	/**
	 * Handle content changed
	 * @inheritDoc
	 * @param {string} newContent
	 * @review
	 */
	syncContent(newContent) {
		if (newContent && (newContent !== this.content)) {
			this._renderContent(newContent);
		}
	}

	/**
	 * Handle editableValues changed
	 * @inheritDoc
	 * @param {object} newEditableValues
	 * @review
	 */
	syncEditableValues(newEditableValues) {
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
			this.languageId,
			this.defaultLanguageId,
			[this._updateEditableStatus]
		);
	}

	/**
	 * Callback executed when languageId property has changed
	 * @inheritDoc
	 * @review
	 */
	syncLanguageId(newLanguageId) {
		if (this.content && (newLanguageId !== this.languageId)) {
			this._renderContent(this.content);
		}
	}

	/**
	 * Propagate store to editables when it's loaded
	 * @review
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
	 * Create instantes of FragmentStyleEditor for each element styled with
	 * background image.
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
	 * Create instances of FragmentEditableField for each editable.
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
						defaultLanguageId: this.defaultLanguageId,
						editableId: editable.id,
						editableValues,
						element: editable,

						events: {
							mapButtonClicked: this._handleMapButtonClick
						},

						fragmentEntryLinkId: this.fragmentEntryLinkId,
						languageId: this.languageId,
						portletNamespace: this.portletNamespace,

						processorsOptions: {
							defaultEditorConfiguration,
							imageSelectorURL: this.imageSelectorURL
						},

						showMapping: this.showMapping,
						store: this.store,
						type: editable.getAttribute('type')
					}
				);
			}
		);
	}

	/**
	 */
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
	 * Destroy existing FragmentEditableField instances.
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
		this.store.dispatchAction(
			UPDATE_EDITABLE_VALUE,
			{
				editableId: event.name,
				editableValue: event.value,
				editableValueId: this.languageId,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			}
		);
	}

	/**
	 * Renders the FragmentEntryLink content parsing with AUI
	 * @param {string} content
	 * @private
	 * @review
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
						this.languageId,
						this.defaultLanguageId,
						[this._updateEditableStatus]
					);
				}
			);
		}
	}

	/**
	 * Runs a set of update functions through the collection of editable values
	 * inside this fragment entry link.
	 * @param {string} languageId The current language id
	 * @param {string} defaultLanguageId The default language id
	 * @param {Array<Function>} updateFunctions The set of update functions to execute for each
	 * 	editable value
	 * @private
	 * @review
	 */
	_update(languageId, defaultLanguageId, updateFunctions) {
		const editableValues = this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

		Object.keys(editableValues).forEach(
			editableId => {
				const editableValue = editableValues[editableId];

				const defaultValue = editableValue[defaultLanguageId] || editableValue.defaultValue;
				const mappedField = editableValue.mappedField || '';
				const value = editableValue[languageId];

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

	/**
	 * Flags a DOM editable section as translated or untranslated compared to
	 * the stored default value for that same editable id.
	 * @param {string} editableId The editable id
	 * @param {string} value The value for the editable section
	 * @param {string} defaultValue
	 * @param {string} mappedField
	 * @private
	 * @review
	 */
	_updateEditableStatus(editableId, value, defaultValue, mappedField) {
		const element = this.element.querySelector(`lfr-editable[id="${editableId}"]`);

		if (element) {
			removeClasses(
				element,
				'mapped',
				'translated',
				'unmapped',
				'untranslated'
			);

			const mapped = Boolean(mappedField);
			const translated = Boolean(!mappedField && value);

			addClasses(element, mapped ? 'mapped' : 'unmapped');
			addClasses(element, translated ? 'translated' : 'untranslated');
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLinkContent.STATE = {

	/**
	 * Fragment content to be rendered
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
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
	 * Default configurations for AlloyEditor instances.
	 * @default {}
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {object}
	 */
	defaultEditorConfigurations: Config.object().value({}),

	/**
	 * Default language id.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */
	defaultLanguageId: Config.string().required(),

	/**
	 * Editable values that should be used instead of the default ones
	 * inside editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!Object}
	 */
	editableValues: Config.object().required(),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkContent
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Image selector url
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	imageSelectorURL: Config.string().required(),

	/**
	 * Currently selected language id.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */
	languageId: Config.string().required(),

	/**
	 * Selected mapping type label
	 * @default {}
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {{
	 *   subtype: {
	 *   	id: !string,
	 *   	label: !string
	 *   },
	 *   type: {
	 *   	id: !string,
	 *   	label: !string
	 *   }
	 * }}
	 */
	selectedMappingTypes: Config
		.shapeOf(
			{
				subtype: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				),
				type: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				)
			}
		)
		.value({}),

	/**
	 * If true, asset mapping is enabled
	 * @default false
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {bool}
	 */
	showMapping: Config.bool().value(false),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {MetalStore}
	 */
	store: Config.instanceOf(MetalStore),

	/**
	 * Portlet namespace needed for prefixing Alloy Editor instances
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	portletNamespace: Config.string().required()
};

Soy.register(FragmentEntryLinkContent, templates);

export {EDITABLE_FRAGMENT_ENTRY_PROCESSOR};
export default FragmentEntryLinkContent;