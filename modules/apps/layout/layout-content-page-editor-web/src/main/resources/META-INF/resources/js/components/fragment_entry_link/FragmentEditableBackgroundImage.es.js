import {Config} from 'metal-state';
import Component from 'metal-component';
import {Store} from '../../store/store.es';

import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	DEFAULT_LANGUAGE_ID_KEY
} from '../../utils/constants';
import EditableBackgroundImageProcessor from '../fragment_processors/EditableBackgroundImageProcessor.es';
import FragmentEditableFieldTooltip from './FragmentEditableFieldTooltip.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {prefixSegmentsExperienceId} from '../../utils/prefixSegmentsExperienceId.es';
import {updateEditableValueAction} from '../../actions/updateEditableValue.es';

const TOOLTIP_BUTTON_IDS = {
	map: 'map',
	remove: 'remove',
	select: 'select'
};

/**
 * FragmentEditableBackgroundImage
 */
class FragmentEditableBackgroundImage extends Component {
	/**
	 * Returns the list of buttons to be shown inside the tooltip.
	 * @param {boolean} showMapping
	 * @param {boolean} showRemoveButton
	 * @return {Array<{id: string, label: string}>}
	 * @review
	 */
	static getButtons(showMapping, showRemoveButton) {
		const buttons = [
			{
				icon: 'pencil',
				id: TOOLTIP_BUTTON_IDS.select,
				label: Liferay.Language.get('select-background')
			}
		];

		if (showMapping) {
			buttons.push({
				icon: 'bolt',
				id: TOOLTIP_BUTTON_IDS.map,
				label: Liferay.Language.get('map-background')
			});
		}

		if (showRemoveButton) {
			buttons.push({
				icon: 'times-circle',
				id: TOOLTIP_BUTTON_IDS.remove,
				label: Liferay.Language.get('remove-background')
			});
		}

		return buttons;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleClick = this._handleClick.bind(this);
		this._handleOutsideTooltipClick = this._handleOutsideTooltipClick.bind(
			this
		);
		this._handleSelectBackgroundImage = this._handleSelectBackgroundImage.bind(
			this
		);
		this._handleTooltipButtonClick = this._handleTooltipButtonClick.bind(
			this
		);

		this.element.addEventListener('click', this._handleClick);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this.element.removeEventListener('click', this._handleClick);
		this._disposeTooltip();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncEditableValues() {
		this._renderBackgroundImage();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncDefaultLanguageId() {
		this._renderBackgroundImage();
	}

	/**
	 * @private
	 * @review
	 */
	_disposeTooltip() {
		if (this._tooltip) {
			this._tooltip.dispose();
			this._tooltip = null;
		}
	}

	/**
	 * Get background image translated value
	 * @private
	 * @review
	 */
	_getBackgroundImageValue() {
		const defaultSegmentsExperienceId = prefixSegmentsExperienceId(
			this.defaultSegmentsExperienceId
		);
		const segmentsExperienceId = prefixSegmentsExperienceId(
			this.segmentsExperienceId
		);

		const segmentedValue =
			this.editableValues[segmentsExperienceId] ||
			this.editableValues[defaultSegmentsExperienceId] ||
			this.editableValues;

		const translatedValue =
			segmentedValue[this.languageId] ||
			segmentedValue[this.defaultLanguageId];

		return translatedValue;
	}

	/**
	 * @private
	 * @review
	 */
	_handleClick(event) {
		if (this._tooltip) {
			this._disposeTooltip();
		} else if (this._shouldShowTooltip(event.target)) {
			this._tooltip = new FragmentEditableFieldTooltip({
				alignElement: this.element,
				buttons: FragmentEditableBackgroundImage.getButtons(
					this.showMapping,
					this._getBackgroundImageValue()
				),
				store: this.store
			});

			this._tooltip.on('buttonClick', this._handleTooltipButtonClick);
			this._tooltip.on(
				'outsideTooltipClick',
				this._handleOutsideTooltipClick
			);
		}
	}

	/**
	 * Decide wether the tooltip should be shown or not.
	 * The tooltip will be shown when user clicks in an element
	 * that is not an editable.
	 * @param {HTMLElement} target The element clicked
	 * @private
	 * @review
	 */
	_shouldShowTooltip(target) {
		const isNotEditable = target.tagName !== 'lfr-editable';

		const hasEditableParent = target.closest('lfr-editable');

		return isNotEditable && !hasEditableParent;
	}

	/**
	 * @private
	 * @review
	 */
	_handleOutsideTooltipClick() {
		this._disposeTooltip();
	}

	/**
	 * @param {string} backgroundImageURL
	 * @private
	 * @review
	 */
	_handleSelectBackgroundImage(backgroundImageURL) {
		this._updateBackgroundImage(backgroundImageURL);
	}

	/**
	 * Handles click events for tooltip buttons.
	 * @param {object} event The tooltip button click.
	 */
	_handleTooltipButtonClick(event) {
		if (event.buttonId === TOOLTIP_BUTTON_IDS.select) {
			EditableBackgroundImageProcessor.init(
				this._handleSelectBackgroundImage,
				this.imageSelectorURL,
				this.portletNamespace
			);
		} else if (event.buttonId === TOOLTIP_BUTTON_IDS.remove) {
			this._updateBackgroundImage('');

			requestAnimationFrame(() => {
				this._disposeTooltip();
			});
		}
	}

	/**
	 * @private
	 * @review
	 */
	_renderBackgroundImage() {
		const translatedValue = this._getBackgroundImageValue();

		EditableBackgroundImageProcessor.render(this.element, translatedValue);
	}

	/**
	 * Dispatches action to update editableValues with new background image url
	 * @param {string} backgroundImageURL
	 */
	_updateBackgroundImage(backgroundImageURL) {
		const defaultSegmentsExperienceId = prefixSegmentsExperienceId(
			this.defaultSegmentsExperienceId
		);
		const segmentsExperienceId = prefixSegmentsExperienceId(
			this.segmentsExperienceId
		);

		this.store.dispatch(
			updateEditableValueAction(
				this.fragmentEntryLinkId,
				this.editableId,
				this.languageId || DEFAULT_LANGUAGE_ID_KEY,
				backgroundImageURL,
				segmentsExperienceId || defaultSegmentsExperienceId,
				BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			)
		);
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEditableBackgroundImage.STATE = {
	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	editableId: Config.string().required(),

	/**
	 * Editable values that should be used instead of the default ones inside
	 * editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {!Object}
	 */
	editableValues: Config.object().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	processor: Config.string().required(),

	/**
	 * If <code>true</code>, the mapping is activated.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {!boolean}
	 */
	showMapping: Config.bool().required(),

	/**
	 * Store instance.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {Store}
	 */
	store: Config.instanceOf(Store)
};

const ConnectedFragmentEditableBackgroundImage = getConnectedComponent(
	FragmentEditableBackgroundImage,
	[
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'imageSelectorURL',
		'languageId',
		'portletNamespace',
		'segmentsExperienceId'
	]
);

export {
	ConnectedFragmentEditableBackgroundImage,
	FragmentEditableBackgroundImage
};
export default ConnectedFragmentEditableBackgroundImage;
