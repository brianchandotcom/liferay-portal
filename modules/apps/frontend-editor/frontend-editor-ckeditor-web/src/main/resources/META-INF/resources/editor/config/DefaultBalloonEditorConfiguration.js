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

const sub = Liferay.Util.sub;

const DEFAULT_BALLOON_EDITOR_CONFIG = {
	extraAllowedContent: '*',
	extraPlugins:
		'itemselector,stylescombo,ballooneditor,videoembed,insertbutton',
	removePlugins: 'contextmenu,link,liststyle,tabletools',
	stylesSet: [
		{
			element: 'p',
			name: Liferay.Language.get('normal'),
		},
		{
			element: 'h1',
			name: sub(Liferay.Language.get('heading-x'), 1),
		},
		{
			element: 'h2',
			name: sub(Liferay.Language.get('heading-x'), 2),
		},
		{
			element: 'pre',
			name: Liferay.Language.get('preformatted-text'),
		},
		{
			element: 'cite',
			name: Liferay.Language.get('cited-work'),
		},
		{
			element: 'code',
			name: Liferay.Language.get('computer-code'),
		},
	],
	title: false,
	toolbarImage:
		'ImageAlignLeft,ImageAlignCenter,ImageAlignRight,LinkAddOrEdit,AltImg',
	toolbarLink: 'LinkRemove,LinkTarget,LinkInput,LinkConfirm,LinkBrowse',
	toolbarTable: 'TableHeaders,TableRow,TableColumn,TableCell,TableDelete',
	toolbarText:
		'Styles,Bold,Italic,Underline,BulletedList,NumberedList,TextLink,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,LineHeight,BGColor,RemoveFormat',
	toolbarVideo: 'VideoAlignLeft,VideoAlignCenter,VideoAlignRight',
};

export default DEFAULT_BALLOON_EDITOR_CONFIG;
