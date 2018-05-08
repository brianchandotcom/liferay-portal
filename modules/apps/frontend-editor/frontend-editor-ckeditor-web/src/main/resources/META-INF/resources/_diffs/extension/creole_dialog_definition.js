CKEDITOR.on(
	'dialogDefinition',
	function(event) {
		if (event.editor === ckEditor) {
			var dialogName = event.data.name;

			var dialogDefinition = event.data.definition;

			var infoTab;

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
			else if (dialogName === 'table' || dialogName === 'tableProperties') {
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
	}
);