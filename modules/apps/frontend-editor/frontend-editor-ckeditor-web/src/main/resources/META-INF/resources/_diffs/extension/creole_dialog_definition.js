/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

CKEDITOR.on(
	'dialogDefinition',
	(event) => {
		if (event.editor === ckEditor) {
			const boundingWindow = event.editor.window;

			const dialogName = event.data.name;

			const dialogDefinition = event.data.definition;

			const dialog = event.data.dialog;

			const onShow = dialogDefinition.onShow;

			const centerDialog = function () {
				const dialogSize = dialog.getSize();

				const x = window.innerWidth / 2 - dialogSize.width / 2;
				const y = window.innerHeight / 2 - dialogSize.height / 2;

				dialog.move(x, y, false);
			};

			dialogDefinition.onShow = function () {
				if (typeof onShow === 'function') {
					onShow.apply(this, arguments);
				}

				centerDialog();
			};

			const debounce = function (fn, delay) {
				return function debounced() {
					const args = arguments;
					clearTimeout(debounced.id);
					debounced.id = setTimeout(() => {
						fn.apply(null, args);
					}, delay);
				};
			};

			boundingWindow.on(
				'resize',
				debounce(() => {
					centerDialog();
				}, 250)
			);

			let infoTab;

			if (dialogName === 'cellProperties') {
				infoTab = dialogDefinition.getContents('info');

				infoTab.remove('bgColor');
				infoTab.remove('bgColorChoose');
				infoTab.remove('borderColor');
				infoTab.remove('borderColorChoose');
				infoTab.remove('colSpan');
				infoTab.remove('hAlign');
				infoTab.remove('height');
				infoTab.remove('htmlHeightType');
				infoTab.remove('rowSpan');
				infoTab.remove('vAlign');
				infoTab.remove('width');
				infoTab.remove('widthType');
				infoTab.remove('wordWrap');

				dialogDefinition.minHeight = 40;
				dialogDefinition.minWidth = 210;
			}
			else if (
				dialogName === 'table' ||
				dialogName === 'tableProperties'
			) {
				infoTab = dialogDefinition.getContents('info');

				infoTab.remove('cmbAlign');
				infoTab.remove('cmbWidthType');
				infoTab.remove('cmbWidthType');
				infoTab.remove('htmlHeightType');
				infoTab.remove('txtBorder');
				infoTab.remove('txtCellPad');
				infoTab.remove('txtCellSpace');
				infoTab.remove('txtHeight');
				infoTab.remove('txtSummary');
				infoTab.remove('txtWidth');

				dialogDefinition.minHeight = 180;
				dialogDefinition.minWidth = 210;
			}
			else if (dialogName === 'image') {
				dialogDefinition.removeContents('Link');
				dialogDefinition.removeContents('advanced');
			}
		}
	},
	null,
	null,
	100
);
