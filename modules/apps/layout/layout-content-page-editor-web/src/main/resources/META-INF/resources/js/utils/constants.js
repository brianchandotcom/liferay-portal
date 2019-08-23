/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * @type {string}
 */
export const BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR =
	'com.liferay.fragment.entry.processor.background.image.BackgroundImageFragmentEntryProcessor';

/**
 * List of editable types and their compatibilities
 * with the corresponding mappeable types
 * @review
 * @see DDMStructureClassType.java for compatible types
 * @type {!object}
 */

export const COMPATIBLE_TYPES = {
	html: [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea',
		'url'
	],

	image: ['ddm-image', 'image'],

	link: [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea',
		'url'
	],

	'rich-text': [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea',
		'url'
	],

	text: [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'text',
		'textarea',
		'url'
	]
};

/**
 * Default language id key
 */
export const DEFAULT_LANGUAGE_ID_KEY = 'defaultValue';

/**
 * Available editable field config keys
 */
export const EDITABLE_FIELD_CONFIG_KEYS = {
	alt: 'alt',
	imageLink: 'imageLink',
	imageSource: 'imageSource',
	imageTarget: 'imageTarget',
	textAlignment: 'textAlignment',
	textColor: 'textColor',
	textStyle: 'textStyle'
};

/**
 * FloatingToolbar panels
 */
export const FLOATING_TOOLBAR_BUTTONS = {
	backgroundColor: {
		icon: 'color-picker',
		id: 'background_color',
		panelId: 'background_color',
		title: Liferay.Language.get('background-color'),
		type: 'panel'
	},

	duplicateFragment: {
		icon: 'paste',
		id: 'duplicate_fragment',
		panelId: 'duplicate_fragment',
		title: Liferay.Language.get('duplicate-fragment'),
		type: 'panel'
	},

	edit: {
		icon: 'pencil',
		id: 'edit',
		title: Liferay.Language.get('edit'),
		type: 'editor'
	},

	fragmentBackgroundImage: {
		icon: 'pencil',
		id: 'fragment_background_image',
		panelId: 'fragment_background_image',
		title: Liferay.Language.get('fragment-background-image'),
		type: 'panel'
	},

	fragmentConfiguration: {
		icon: 'cog',
		id: 'fragment_configuration',
		panelId: 'fragment_configuration',
		title: Liferay.Language.get('fragment-configuration'),
		type: 'panel'
	},

	imageProperties: {
		icon: 'pencil',
		id: 'image_properties',
		panelId: 'image_properties',
		title: Liferay.Language.get('image-properties'),
		type: 'panel'
	},

	layoutBackgroundImage: {
		icon: 'picture',
		id: 'layout_background_image',
		panelId: 'layout_background_image',
		title: Liferay.Language.get('layout-background-image'),
		type: 'panel'
	},

	link: {
		icon: 'link',
		id: 'link',
		panelId: 'link',
		title: Liferay.Language.get('link'),
		type: 'panel'
	},

	map: {
		icon: 'bolt',
		id: 'mapping',
		panelId: 'mapping',
		title: Liferay.Language.get('map'),
		type: 'panel'
	},

	removeFragment: {
		icon: 'times-circle',
		id: 'remove_fragment',
		panelId: 'remove_fragment',
		title: Liferay.Language.get('remove-fragment'),
		type: 'panel'
	},

	spacing: {
		icon: 'table',
		id: 'spacing',
		panelId: 'spacing',
		title: Liferay.Language.get('spacing'),
		type: 'panel'
	}
};

/**
 * @type {string}
 */
export const EDITABLE_FRAGMENT_ENTRY_PROCESSOR =
	'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

/**
 * Fragments Editor dragging class
 * @review
 * @type {string}
 */
export const FRAGMENTS_EDITOR_DRAGGING_CLASS = 'dragging';

/**
 * Fragments Editor item borders
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ITEM_BORDERS = {
	bottom: 'fragments-editor-border-bottom',
	top: 'fragments-editor-border-top'
};

/**
 * Fragments Editor item types
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ITEM_TYPES = {
	backgroundImageEditable: 'fragments-editor-background-image-editable',
	column: 'fragments-editor-column',
	editable: 'fragments-editor-editable-field',
	fragment: 'fragments-editor-fragment',
	fragmentList: 'fragments-editor-fragment-list',
	mappedItem: 'fragments-editor-mapped-item',
	row: 'fragments-editor-row'
};

/**
 * Fragments Editor row types
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ROW_TYPES = {
	componentRow: '1',
	sectionRow: '0'
};

/**
 * @type {string}
 */
export const FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
	'com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor';

/**
 * Available mapping source type ids
 */
export const MAPPING_SOURCE_TYPE_IDS = {
	content: 'specific_content',
	structure: 'structure'
};

/**
 * Available attributes for target config key
 */
export const TARGET_TYPES = [
	{
		label: Liferay.Language.get('self'),
		targetTypeId: '_self'
	},
	{
		label: Liferay.Language.get('blank'),
		targetTypeId: '_blank'
	},
	{
		label: Liferay.Language.get('parent'),
		targetTypeId: '_parent'
	},
	{
		label: Liferay.Language.get('top'),
		targetTypeId: '_top'
	}
];

/**
 * Available values for textAlignmentOptions config key
 */
export const TEXT_ALIGNMENT_OPTIONS = [
	{
		label: Liferay.Language.get('left'),
		textAlignmentId: 'left'
	},
	{
		label: Liferay.Language.get('center'),
		textAlignmentId: 'center'
	},
	{
		label: Liferay.Language.get('justify'),
		textAlignmentId: 'justify'
	},
	{
		label: Liferay.Language.get('right'),
		textAlignmentId: 'right'
	}
];

/**
 * Available values for textStyle config key
 */
export const TEXT_STYLES = [
	{
		label: Liferay.Language.get('normal'),
		textStyleId: ''
	},
	{
		label: Liferay.Language.get('small'),
		textStyleId: 'small'
	},
	{
		label: Liferay.Language.get('lead'),
		textStyleId: 'lead'
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('heading-x'), '1'),
		textStyleId: 'h1'
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('heading-x'), '2'),
		textStyleId: 'h2'
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('heading-x'), '3'),
		textStyleId: 'h3'
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('heading-x'), '4'),
		textStyleId: 'h4'
	}
];
