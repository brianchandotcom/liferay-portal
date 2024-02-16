/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

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
