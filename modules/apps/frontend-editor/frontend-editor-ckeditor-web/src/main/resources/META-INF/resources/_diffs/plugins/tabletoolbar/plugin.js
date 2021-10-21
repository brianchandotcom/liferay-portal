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

(function () {
	function getSelectedCells(selection) {
		const ranges = selection.getRanges();
		const selectedCells = [];
		const database = {};

		function moveOutOfCellGuard(node) {
			const cellNodeRegex = /^(?:td|th)$/;

			if (selectedCells.length > 0) {
				return;
			}

			if (
				node.type == CKEDITOR.NODE_ELEMENT &&
				cellNodeRegex.test(node.getName()) &&
				!node.getCustomData('selected_cell')
			) {
				CKEDITOR.dom.element.setMarker(
					database,
					node,
					'selected_cell',
					true
				);
				selectedCells.push(node);
			}
		}

		for (let i = 0; i < ranges.length; i++) {
			const range = ranges[i];

			if (range.collapsed) {
				const startNode = range.getCommonAncestor();
				const nearestCell =
					startNode.getAscendant('td', true) ||
					startNode.getAscendant('th', true);
				if (nearestCell) {
					selectedCells.push(nearestCell);
				}
			}
			else {
				const walker = new CKEDITOR.dom.walker(range);
				let node;
				walker.guard = moveOutOfCellGuard;

				while ((node = walker.next())) {
					if (
						node.type != CKEDITOR.NODE_ELEMENT ||
						!node.is(CKEDITOR.dtd.table)
					) {
						const parent =
							node.getAscendant('td', true) ||
							node.getAscendant('th', true);
						if (parent && !parent.getCustomData('selected_cell')) {
							CKEDITOR.dom.element.setMarker(
								database,
								parent,
								'selected_cell',
								true
							);
							selectedCells.push(parent);
						}
					}
				}
			}
		}

		CKEDITOR.dom.element.clearAllMarkers(database);

		return selectedCells;
	}

	function getFocusElementAfterDelCells(cellsToDelete) {
		let i = 0;

		const last = cellsToDelete.length - 1;

		const database = {};

		let cell;

		let focusedCell;

		let tr;

		while ((cell = cellsToDelete[i++])) {
			CKEDITOR.dom.element.setMarker(database, cell, 'delete_cell', true);
		}

		i = 0;

		while ((cell = cellsToDelete[i++])) {
			if (
				((focusedCell = cell.getPrevious()) &&
					!focusedCell.getCustomData('delete_cell')) ||
				((focusedCell = cell.getNext()) &&
					!focusedCell.getCustomData('delete_cell'))
			) {
				CKEDITOR.dom.element.clearAllMarkers(database);

				return focusedCell;
			}
		}

		CKEDITOR.dom.element.clearAllMarkers(database);

		tr = cellsToDelete[0].getParent();

		if ((tr = tr.getPrevious())) {
			return tr.getLast();
		}

		tr = cellsToDelete[last].getParent();

		if ((tr = tr.getNext())) {
			return tr.getChild(0);
		}

		return null;
	}

	function insertRow(editor, insertBefore) {
		const selection = editor.getSelection();

		const cells = getSelectedCells(selection);

		const firstCell = cells[0];

		const table = firstCell.getAscendant('table');

		const doc = firstCell.getDocument();

		const startRow = cells[0].getParent();

		const startRowIndex = startRow.$.rowIndex;

		const lastCell = cells[cells.length - 1];

		const endRowIndex =
			lastCell.getParent().$.rowIndex + lastCell.$.rowSpan - 1;

		const endRow = new CKEDITOR.dom.element(table.$.rows[endRowIndex]);

		const rowIndex = insertBefore ? startRowIndex : endRowIndex;

		const row = insertBefore ? startRow : endRow;

		const map = CKEDITOR.tools.buildTableMap(table);

		const cloneRow = map[rowIndex];

		const nextRow = insertBefore ? map[rowIndex - 1] : map[rowIndex + 1];

		const width = map[0].length;

		const newRow = doc.createElement('tr');

		for (let i = 0; cloneRow[i] && i < width; i++) {
			let cell;

			if (
				cloneRow[i].rowSpan > 1 &&
				nextRow &&
				cloneRow[i] == nextRow[i]
			) {
				cell = cloneRow[i];
				cell.rowSpan += 1;
			}
			else {
				cell = new CKEDITOR.dom.element(cloneRow[i]).clone();
				cell.removeAttribute('rowSpan');
				cell.appendBogus();
				newRow.append(cell);
				cell = cell.$;
			}

			i += cell.colSpan - 1;
		}

		if (insertBefore) {
			newRow.insertBefore(row);
		}
		else {
			newRow.insertAfter(row);
		}

		const cell = new CKEDITOR.dom.element(newRow.$).getChild(
			cells[0] ? cells[0].$.cellIndex : 0
		);

		selectElement(editor, cell);
	}

	function deleteRows(selectionOrRow) {
		if (selectionOrRow instanceof CKEDITOR.dom.selection) {
			const cells = getSelectedCells(selectionOrRow);

			const firstCell = cells[0];

			const table = firstCell.getAscendant('table');

			const map = CKEDITOR.tools.buildTableMap(table);

			const startRow = cells[0].getParent();

			const startRowIndex = startRow.$.rowIndex;

			const lastCell = cells[cells.length - 1];

			const endRowIndex =
				lastCell.getParent().$.rowIndex + lastCell.$.rowSpan - 1;

			const rowsToDelete = [];

			for (let i = startRowIndex; i <= endRowIndex; i++) {
				const mapRow = map[i];

				const row = new CKEDITOR.dom.element(table.$.rows[i]);

				for (let j = 0; j < mapRow.length; j++) {
					const cell = new CKEDITOR.dom.element(mapRow[j]);

					const cellRowIndex = cell.getParent().$.rowIndex;

					if (cell.$.rowSpan == 1) {
						cell.remove();
					}
					else {
						cell.$.rowSpan -= 1;

						if (cellRowIndex == i) {
							const nextMapRow = map[i + 1];
							if (nextMapRow[j - 1]) {
								cell.insertAfter(
									new CKEDITOR.dom.element(nextMapRow[j - 1])
								);
							}
							else {
								new CKEDITOR.dom.element(
									table.$.rows[i + 1]
								).append(cell, 1);
							}
						}
					}

					j += cell.$.colSpan - 1;
				}

				rowsToDelete.push(row);
			}

			const rows = table.$.rows;

			const cursorPosition = new CKEDITOR.dom.element(
				rows[endRowIndex + 1] ||
					(startRowIndex > 0 ? rows[startRowIndex - 1] : null) ||
					table.$.parentNode
			);

			for (let i = rowsToDelete.length; i >= 0; i--) {
				deleteRows(rowsToDelete[i]);
			}

			return cursorPosition;
		}
		else if (selectionOrRow instanceof CKEDITOR.dom.element) {
			const table = selectionOrRow.getAscendant('table');

			if (table.$.rows.length == 1) {
				table.remove();
			}
			else {
				selectionOrRow.remove();
			}
		}

		return null;
	}

	function getCellColIndex(cell, isStart) {
		const row = cell.getParent();

		const rowCells = row.$.cells;

		let colIndex = 0;
		for (let i = 0; i < rowCells.length; i++) {
			const mapCell = rowCells[i];
			colIndex += isStart ? 1 : mapCell.colSpan;
			if (mapCell == cell.$) {
				break;
			}
		}

		return colIndex - 1;
	}

	function getColumnsIndices(cells, isStart) {
		let columnsIndices = isStart ? Infinity : 0;

		for (let i = 0; i < cells.length; i++) {
			const colIndex = getCellColIndex(cells[i], isStart);

			if (
				isStart ? colIndex < columnsIndices : colIndex > columnsIndices
			) {
				columnsIndices = colIndex;
			}
		}

		return columnsIndices;
	}

	function insertColumn(editor, insertBefore) {
		const selection = editor.getSelection();

		const cells = getSelectedCells(selection);

		const firstCell = cells[0];

		const table = firstCell.getAscendant('table');

		const startCol = getColumnsIndices(cells, 1);

		const lastCol = getColumnsIndices(cells);

		const colIndex = insertBefore ? startCol : lastCol;

		const map = CKEDITOR.tools.buildTableMap(table);

		const cloneCol = [];

		const nextCol = [];

		const height = map.length;

		for (let i = 0; i < height; i++) {
			cloneCol.push(map[i][colIndex]);
			const nextCell = insertBefore
				? map[i][colIndex - 1]
				: map[i][colIndex + 1];
			nextCol.push(nextCell);
		}

		const insertedCells = [];
		for (let i = 0; i < height; i++) {
			let cell;

			if (!cloneCol[i]) {
				continue;
			}

			if (cloneCol[i].colSpan > 1 && nextCol[i] == cloneCol[i]) {
				cell = cloneCol[i];
				cell.colSpan += 1;
			}
			else {
				cell = new CKEDITOR.dom.element(cloneCol[i]).clone();
				cell.removeAttribute('colSpan');
				cell.appendBogus();
				cell[insertBefore ? 'insertBefore' : 'insertAfter'].call(
					cell,
					new CKEDITOR.dom.element(cloneCol[i])
				);
				cell = cell.$;
			}

			insertedCells[i] = cell;

			i += cell.rowSpan - 1;
		}

		const cell = new CKEDITOR.dom.element(
			insertedCells[firstCell.getParent().$.rowIndex]
		);

		selectElement(editor, cell);
	}

	function selectElement(editor, element) {
		const range = editor.createRange();

		range.moveToPosition(element, CKEDITOR.POSITION_AFTER_START);
		editor.getSelection().selectRanges([range]);
	}

	function deleteColumns(selectionOrCell) {
		const cells = getSelectedCells(selectionOrCell);

		const firstCell = cells[0];

		const lastCell = cells[cells.length - 1];

		const table = firstCell.getAscendant('table');

		const map = CKEDITOR.tools.buildTableMap(table);

		let startColIndex;

		let endColIndex;

		const rowsToDelete = [];

		let rows;

		for (let i = 0, rows = map.length; i < rows; i++) {
			for (let j = 0, cols = map[i].length; j < cols; j++) {
				if (map[i][j] == firstCell.$) {
					startColIndex = j;
				}
				if (map[i][j] == lastCell.$) {
					endColIndex = j;
				}
			}
		}

		for (let i = startColIndex; i <= endColIndex; i++) {
			for (let j = 0; j < map.length; j++) {
				const mapRow = map[j];

				const row = new CKEDITOR.dom.element(table.$.rows[j]);

				const cell = new CKEDITOR.dom.element(mapRow[i]);

				if (cell.$) {
					if (cell.$.colSpan == 1) {
						cell.remove();
					}
					else {
						cell.$.colSpan -= 1;
					}

					j += cell.$.rowSpan - 1;

					if (!row.$.cells.length) {
						rowsToDelete.push(row);
					}
				}
			}
		}

		const firstRowCells = table.$.rows[0] && table.$.rows[0].cells;

		const cursorPosition = new CKEDITOR.dom.element(
			firstRowCells[startColIndex] ||
				(startColIndex
					? firstRowCells[startColIndex - 1]
					: table.$.parentNode)
		);

		if (rowsToDelete.length == rows) {
			table.remove();
		}

		return cursorPosition;
	}

	function insertCell(selection, insertBefore) {
		const startElement = selection.getStartElement();
		const cell =
			startElement.getAscendant('td', 1) ||
			startElement.getAscendant('th', 1);

		if (!cell) {
			return;
		}

		const newCell = cell.clone();

		newCell.appendBogus();

		if (insertBefore) {
			newCell.insertBefore(cell);
		}
		else {
			newCell.insertAfter(cell);
		}
	}

	function deleteCells(selectionOrCell) {
		if (selectionOrCell instanceof CKEDITOR.dom.selection) {
			const cellsToDelete = getSelectedCells(selectionOrCell);
			const table =
				cellsToDelete[0] && cellsToDelete[0].getAscendant('table');
			const cellToFocus = getFocusElementAfterDelCells(cellsToDelete);

			for (let i = cellsToDelete.length - 1; i >= 0; i--) {
				deleteCells(cellsToDelete[i]);
			}

			if (cellToFocus) {
				placeCursorInCell(cellToFocus, true);
			}
			else if (table) {
				table.remove();
			}
		}
		else if (selectionOrCell instanceof CKEDITOR.dom.element) {
			const tr = selectionOrCell.getParent();
			if (tr.getChildCount() == 1) {
				tr.remove();
			}
			else {
				selectionOrCell.remove();
			}
		}
	}

	function trimCell(cell) {
		const bogus = cell.getBogus();

		if (bogus) {
			bogus.remove();
		}
		cell.trim();
	}

	function placeCursorInCell(cell, placeAtEnd) {
		const docInner = cell.getDocument();

		const docOuter = CKEDITOR.document;

		if (CKEDITOR.env.ie && CKEDITOR.env.version == 10) {
			docOuter.focus();
			docInner.focus();
		}

		const range = new CKEDITOR.dom.range(docInner);
		if (
			!range['moveToElementEdit' + (placeAtEnd ? 'End' : 'Start')](cell)
		) {
			range.selectNodeContents(cell);
			range.collapse(placeAtEnd ? false : true);
		}

		range.select(true);
	}

	function cellInRow(tableMap, rowIndex, cell) {
		const row = tableMap[rowIndex];

		if (typeof cell == 'undefined') {
			return row;
		}

		for (let cellIndex = 0; row && cellIndex < row.length; cellIndex++) {
			if (cell.is && row[cellIndex] == cell.$) {
				return cellIndex;
			}
			else if (cellIndex == cell) {
				return new CKEDITOR.dom.element(row[cellIndex]);
			}
		}

		return cell.is ? -1 : null;
	}

	function cellInCol(tableMap, colIndex) {
		const oCol = [];

		for (let rowIndex = 0; rowIndex < tableMap.length; rowIndex++) {
			const row = tableMap[rowIndex];

			oCol.push(row[colIndex]);

			if (row[colIndex].rowSpan > 1) {
				rowIndex += row[colIndex].rowSpan - 1;
			}
		}

		return oCol;
	}

	function mergeCells(selection, mergeDirection, isDetect) {
		const cells = getSelectedCells(selection);

		let commonAncestor;

		if (
			(mergeDirection ? cells.length != 1 : cells.length < 2) ||
			((commonAncestor = selection.getCommonAncestor()) &&
				commonAncestor.type == CKEDITOR.NODE_ELEMENT &&
				commonAncestor.is('table'))
		) {
			return false;
		}

		let cell;

		const firstCell = cells[0];

		const table = firstCell.getAscendant('table');

		const map = CKEDITOR.tools.buildTableMap(table);

		const mapHeight = map.length;

		const mapWidth = map[0].length;

		const startRow = firstCell.getParent().$.rowIndex;

		const startColumn = cellInRow(map, startRow, firstCell);

		if (mergeDirection) {
			let targetCell;
			try {
				const rowspan =
					parseInt(firstCell.getAttribute('rowspan'), 10) || 1;
				const colspan =
					parseInt(firstCell.getAttribute('colspan'), 10) || 1;

				targetCell =
					map[
						mergeDirection == 'up'
							? startRow - rowspan
							: mergeDirection == 'down'
							? startRow + rowspan
							: startRow
					][
						mergeDirection == 'left'
							? startColumn - colspan
							: mergeDirection == 'right'
							? startColumn + colspan
							: startColumn
					];
			}
			catch (er) {
				return false;
			}

			if (!targetCell || firstCell.$ == targetCell) {
				return false;
			}

			cells[
				mergeDirection == 'up' || mergeDirection == 'left'
					? 'unshift'
					: 'push'
			](new CKEDITOR.dom.element(targetCell));
		}

		const doc = firstCell.getDocument();

		let lastRowIndex = startRow;

		let totalRowSpan = 0;

		let totalColSpan = 0;

		const frag = !isDetect && new CKEDITOR.dom.documentFragment(doc);

		let dimension = 0;

		for (let i = 0; i < cells.length; i++) {
			cell = cells[i];

			const tr = cell.getParent();

			const cellFirstChild = cell.getFirst();

			const colSpan = cell.$.colSpan;

			const rowSpan = cell.$.rowSpan;

			const rowIndex = tr.$.rowIndex;

			const colIndex = cellInRow(map, rowIndex, cell);

			dimension += colSpan * rowSpan;

			totalColSpan = Math.max(
				totalColSpan,
				colIndex - startColumn + colSpan
			);

			totalRowSpan = Math.max(
				totalRowSpan,
				rowIndex - startRow + rowSpan
			);

			if (!isDetect) {
				if ((trimCell(cell), cell.getChildren().count())) {
					if (
						rowIndex != lastRowIndex &&
						cellFirstChild &&
						!(
							cellFirstChild.isBlockBoundary &&
							cellFirstChild.isBlockBoundary({br: 1})
						)
					) {
						const last = frag.getLast(
							CKEDITOR.dom.walker.whitespaces(true)
						);
						if (last && !(last.is && last.is('br'))) {
							frag.append('br');
						}
					}

					cell.moveChildren(frag);
				}
				if (i) {
					cell.remove();
				}
				else {
					cell.setHtml('');
				}
			}
			lastRowIndex = rowIndex;
		}

		if (!isDetect) {
			frag.moveChildren(firstCell);

			firstCell.appendBogus();

			if (totalColSpan >= mapWidth) {
				firstCell.removeAttribute('rowSpan');
			}
			else {
				firstCell.$.rowSpan = totalRowSpan;
			}

			if (totalRowSpan >= mapHeight) {
				firstCell.removeAttribute('colSpan');
			}
			else {
				firstCell.$.colSpan = totalColSpan;
			}

			const trs = new CKEDITOR.dom.nodeList(table.$.rows);

			let count = trs.count();

			for (let i = count - 1; i >= 0; i--) {
				const tailTr = trs.getItem(i);

				if (!tailTr.$.cells.length) {
					tailTr.remove();
					count++;
					continue;
				}
			}

			return firstCell;
		}
		else {
			return totalRowSpan * totalColSpan == dimension;
		}
	}

	function verticalSplitCell(selection, isDetect) {
		const cells = getSelectedCells(selection);
		if (cells.length > 1) {
			return false;
		}
		else if (isDetect) {
			return true;
		}

		const cell = cells[0];

		const tr = cell.getParent();

		const table = tr.getAscendant('table');

		const map = CKEDITOR.tools.buildTableMap(table);

		const rowIndex = tr.$.rowIndex;

		const colIndex = cellInRow(map, rowIndex, cell);

		const rowSpan = cell.$.rowSpan;

		let newCell;

		let newRowSpan;

		let newCellRowSpan;

		let newRowIndex;

		if (rowSpan > 1) {
			newRowSpan = Math.ceil(rowSpan / 2);
			newCellRowSpan = Math.floor(rowSpan / 2);
			newRowIndex = rowIndex + newRowSpan;
			const newCellTr = new CKEDITOR.dom.element(
				table.$.rows[newRowIndex]
			);

			const newCellRow = cellInRow(map, newRowIndex);

			let candidateCell;

			newCell = cell.clone();

			for (let c = 0; c < newCellRow.length; c++) {
				candidateCell = newCellRow[c];

				if (candidateCell.parentNode == newCellTr.$ && c > colIndex) {
					newCell.insertBefore(
						new CKEDITOR.dom.element(candidateCell)
					);
					break;
				}
				else {
					candidateCell = null;
				}
			}

			if (!candidateCell) {
				newCellTr.append(newCell);
			}
		}
		else {
			newCellRowSpan = newRowSpan = 1;

			const newCellTr = tr.clone();
			newCellTr.insertAfter(tr);
			newCellTr.append((newCell = cell.clone()));

			const cellsInSameRow = cellInRow(map, rowIndex);
			for (let i = 0; i < cellsInSameRow.length; i++) {
				cellsInSameRow[i].rowSpan++;
			}
		}

		newCell.appendBogus();

		cell.$.rowSpan = newRowSpan;
		newCell.$.rowSpan = newCellRowSpan;
		if (newRowSpan == 1) {
			cell.removeAttribute('rowSpan');
		}
		if (newCellRowSpan == 1) {
			newCell.removeAttribute('rowSpan');
		}

		return newCell;
	}

	function horizontalSplitCell(selection, isDetect) {
		const cells = getSelectedCells(selection);
		if (cells.length > 1) {
			return false;
		}
		else if (isDetect) {
			return true;
		}

		const cell = cells[0];

		const tr = cell.getParent();

		const table = tr.getAscendant('table');

		const map = CKEDITOR.tools.buildTableMap(table);

		const rowIndex = tr.$.rowIndex;

		const colIndex = cellInRow(map, rowIndex, cell);

		const colSpan = cell.$.colSpan;

		let newColSpan;

		let newCellColSpan;

		if (colSpan > 1) {
			newColSpan = Math.ceil(colSpan / 2);
			newCellColSpan = Math.floor(colSpan / 2);
		}
		else {
			newCellColSpan = newColSpan = 1;
			const cellsInSameCol = cellInCol(map, colIndex);
			for (let i = 0; i < cellsInSameCol.length; i++) {
				cellsInSameCol[i].colSpan++;
			}
		}
		const newCell = cell.clone();
		newCell.insertAfter(cell);
		newCell.appendBogus();

		cell.$.colSpan = newColSpan;
		newCell.$.colSpan = newCellColSpan;
		if (newColSpan == 1) {
			cell.removeAttribute('colSpan');
		}
		if (newCellColSpan == 1) {
			newCell.removeAttribute('colSpan');
		}

		return newCell;
	}

	const pluginName = 'tabletoolbar';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.plugins.add(pluginName, {
		getSelectedCells,
		icons: 'add-row, add-column, add-cell',
		init(editor) {
			function createDef(def) {
				return CKEDITOR.tools.extend(def || {}, {
					contextSensitive: 1,
					refresh(editor, path) {
						this.setState(
							path.contains({td: 1, th: 1}, 1)
								? CKEDITOR.TRISTATE_OFF
								: CKEDITOR.TRISTATE_DISABLED
						);
					},
				});
			}

			function addCmd(config, def) {
				const commandName = config.commandName || '';
				const editorFocus = config.editorFocus || 0;
				const name = config.name;
				const label = config.label;
				const icon = config.icon;
				const requiredContent = config.requiredContent;
				const panel = config.panel || {};
				const title = config.title || '';

				let cmd = editor.getCommand(commandName);

				if (cmd) {
					return;
				}

				cmd = editor.addCommand(commandName, def);

				editor.addFeature(cmd);

				const panelId = CKEDITOR.tools.getNextId() + '_panel';

				config.panelId = panelId;

				let panelBlock;

				panel.className = 'lfr-table-panel';

				editor.ui.add(name, CKEDITOR.UI_PANELBUTTON, {
					command: commandName,
					editorFocus,
					icon,
					label,
					onBlock(panel, block) {
						panelBlock = block;
						block.autosize = true;
						block.element.setHtml(renderPanel(config));
					},
					onClose() {
						panelBlock.element.hide();
					},
					onOpen() {
						panelBlock.element.show();

						return panelBlock;
					},
					panel,
					requiredContent,
					title,
				});
			}

			function renderPanel(config) {
				const LIST_TPL = new CKEDITOR.template(`
					<ul aria-label='{ariaLabel}' class='cke_panel_list' id="{panelId}" role='{role}'>
						${config.panel.links
							.map(({clickFn, commandName}) => {
								const LIST_ITEM_TPL = new CKEDITOR.template(`
										<li class='cke_panel_listItem' role='presentation'>
											<a onClick='CKEDITOR.tools.callFunction({clickFn}, this); return false;' tablIndex='' role='option'>
												{commandName}
											</a>
										</li>
									`);

								return LIST_ITEM_TPL.output({
									clickFn,
									commandName,
								});
							})
							.join('')}
					</ul>`);

				return LIST_TPL.output({
					ariaLabel: config.panel.attributes['aria-label'],
					panelId: config.panelId,
					role: config.panel.attributes.role,
				});
			}

			function rowDelete() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('rowDelete');
				});
			}

			function rowInsertBefore() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('rowInsertBefore');
				});
			}

			function rowInsertAfter() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('rowInsertAfter');
				});
			}

			function columnDelete() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('columnDelete');
				});
			}

			function columnInsertBefore() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('columnInsertBefore');
				});
			}

			function columnInsertAfter() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('columnInsertAfter');
				});
			}

			function cellDelete() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellDelete');
				});
			}

			function cellMerge() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellMerge');
				});
			}

			function cellMergeRight() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellMergeRight');
				});
			}

			function cellMergeDown() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellMergeDown');
				});
			}

			function cellVerticalSplit() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellVerticalSplit');
				});
			}

			function cellHorizontalSplit() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellHorizontalSplit');
				});
			}

			function cellInsertBefore() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellInsertBefore');
				});
			}

			function cellInsertAfter() {
				return CKEDITOR.tools.addFunction(() => {
					editor.execCommand('cellInsertAfter');
				});
			}

			addCmd(
				{
					commandName: 'tableRow',
					editorFocus: 0,
					icon: 'add-row',
					name: 'TableRow',
					panel: {
						attributes: {
							'aria-label': editor.lang.table.row.menu,
							'role': 'listbox',
						},
						css: [CKEDITOR.skin.getPath('editor')].concat(
							editor.config.contentsCss
						),
						links: [
							{
								clickFn: rowDelete(),
								commandName: editor.lang.table.row.deleteRow,
							},
							{
								clickFn: rowInsertBefore(),
								commandName: editor.lang.table.row.insertBefore,
							},
							{
								clickFn: rowInsertAfter(),
								commandName: editor.lang.table.row.insertAfter,
							},
						],
					},
					requiredContent: 'table',
					title: editor.lang.table.row.menu,
				},
				createDef({
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'rowDelete',
					name: 'RowDelete',
				},
				createDef({
					exec(editor) {
						const selection = editor.getSelection();
						placeCursorInCell(deleteRows(selection));
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'rowInsertBefore',
					name: 'RowInsertBefore',
				},
				createDef({
					exec(editor) {
						insertRow(editor, true);
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'rowInsertAfter',
					name: 'RowInsertAfter',
				},
				createDef({
					exec(editor) {
						insertRow(editor);
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'tableColumn',
					editorFocus: 0,
					icon: 'add-column',
					label: 'tableColumn',
					name: 'TableColumn',
					panel: {
						attributes: {
							'aria-label': editor.lang.table.column.menu,
							'role': 'listbox',
						},
						css: [CKEDITOR.skin.getPath('editor')].concat(
							editor.config.contentsCss
						),
						links: [
							{
								clickFn: columnDelete(),
								commandName:
									editor.lang.table.column.deleteColumn,
							},
							{
								clickFn: columnInsertBefore(),
								commandName:
									editor.lang.table.column.insertBefore,
							},
							{
								clickFn: columnInsertAfter(),
								commandName:
									editor.lang.table.column.insertAfter,
							},
						],
					},
					requiredContent: 'table',
					title: editor.lang.table.column.menu,
				},
				createDef({
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'columnDelete',
					name: 'ColumnDelete',
				},
				createDef({
					exec(editor) {
						const selection = editor.getSelection();
						const element = deleteColumns(selection);
						if (element) {
							placeCursorInCell(element, true);
						}
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'columnInsertBefore',
					name: 'ColumnInsertBefore',
				},
				createDef({
					exec(editor) {
						insertColumn(editor, true);
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'columnInsertAfter',
					name: 'ColumnInsertAfter',
				},
				createDef({
					exec(editor) {
						insertColumn(editor);
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'tableCell',
					editorFocus: 0,
					icon: 'add-cell',
					label: 'tableCell',
					name: 'TableCell',
					panel: {
						attributes: {
							'aria-label': editor.lang.table.cell.menu,
							'role': 'listbox',
						},
						css: [CKEDITOR.skin.getPath('editor')].concat(
							editor.config.contentsCss
						),
						links: [
							{
								clickFn: cellDelete(),
								commandName: editor.lang.table.cell.deleteCell,
							},
							{
								clickFn: cellMerge(),
								commandName: editor.lang.table.cell.merge,
							},
							{
								clickFn: cellMergeRight(),
								commandName: editor.lang.table.cell.mergeRight,
							},
							{
								clickFn: cellMergeDown(),
								commandName: editor.lang.table.cell.mergeDown,
							},
							{
								clickFn: cellVerticalSplit(),
								commandName:
									editor.lang.table.cell.splitVertical,
							},
							{
								clickFn: cellHorizontalSplit(),
								commandName:
									editor.lang.table.cell.splitHorizontal,
							},
							{
								clickFn: cellInsertBefore(),
								commandName:
									editor.lang.table.cell.insertBefore,
							},
							{
								clickFn: cellInsertAfter(),
								commandName: editor.lang.table.cell.insertAfter,
							},
						],
					},
					requiredContent: 'table',
					title: editor.lang.table.cell.menu,
				},
				createDef({
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'cellDelete',
					name: 'CellDelete',
				},
				createDef({
					exec(editor) {
						const selection = editor.getSelection();
						deleteCells(selection);
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'cellMerge',
					name: 'CellMerge',
				},
				createDef({
					allowedContent: 'td[colspan,rowspan]',
					exec(editor) {
						placeCursorInCell(
							mergeCells(editor.getSelection()),
							true
						);
					},
					requiredContent: 'td[colspan,rowspan]',
				})
			);

			addCmd(
				{
					commandName: 'cellMergeRight',
					name: 'CellMergeRight',
				},
				createDef({
					allowedContent: 'td[colspan]',
					exec(editor) {
						placeCursorInCell(
							mergeCells(editor.getSelection(), 'right'),
							true
						);
					},
					requiredContent: 'td[colspan]',
				})
			);

			addCmd(
				{
					commandName: 'cellMergeDown',
					name: 'CellMergeDown',
				},
				createDef({
					allowedContent: 'td[rowspan]',
					exec(editor) {
						placeCursorInCell(
							mergeCells(editor.getSelection(), 'down'),
							true
						);
					},
					requiredContent: 'td[rowspan]',
				})
			);

			addCmd(
				{
					commandName: 'cellVerticalSplit',
					name: 'CellVerticalSplit',
				},
				createDef({
					allowedContent: 'td[rowspan]',
					exec(editor) {
						placeCursorInCell(
							verticalSplitCell(editor.getSelection())
						);
					},
					requiredContent: 'td[rowspan]',
				})
			);

			addCmd(
				{
					commandName: 'cellHorizontalSplit',
					name: 'CellHorizontalSplit',
				},
				createDef({
					allowedContent: 'td[colspan]',
					exec(editor) {
						placeCursorInCell(
							horizontalSplitCell(editor.getSelection())
						);
					},
					requiredContent: 'td[colspan]',
				})
			);

			addCmd(
				{
					commandName: 'cellInsertBefore',
					name: 'CellInsertBefore',
				},
				createDef({
					exec(editor) {
						const selection = editor.getSelection();
						insertCell(selection, true);
					},
					requiredContent: 'table',
				})
			);

			addCmd(
				{
					commandName: 'cellInsertAfter',
					name: 'CellInsertAfter',
				},
				createDef({
					exec(editor) {
						const selection = editor.getSelection();
						insertCell(selection);
					},
					requiredContent: 'table',
				})
			);
		},
		requires: 'panelbutton,floatpanel',
	});
})();

/**
 * Create a two-dimension array that reflects the actual layout of table cells,
 * with cell spans, with mappings to the original td elements.
 *
 * @param {CKEDITOR.dom.element} table
 * @member CKEDITOR.tools
 */
CKEDITOR.tools.buildTableMap = function (table) {
	const rows = table.$.rows;

	let rowIndex = -1;

	const tableMap = [];

	for (let i = 0; i < rows.length; i++) {
		rowIndex++;

		if (!tableMap[rowIndex]) {
			tableMap[rowIndex] = [];
		}

		let columnIndex = -1;

		for (let j = 0; j < rows[i].cells.length; j++) {
			const cellElement = rows[i].cells[j];

			columnIndex++;
			while (tableMap[rowIndex][columnIndex]) {
				columnIndex++;
			}

			const cellElementColSpan = isNaN(cellElement.colSpan)
				? 1
				: cellElement.colSpan;
			const rowElementColSpan = isNaN(cellElement.rowSpan)
				? 1
				: cellElement.rowSpan;

			for (
				let rowSpanCounter = 0;
				rowSpanCounter < rowElementColSpan;
				rowSpanCounter++
			) {
				if (!tableMap[rowIndex + rowSpanCounter]) {
					tableMap[rowIndex + rowSpanCounter] = [];
				}

				for (
					let colSpanCounter = 0;
					colSpanCounter < cellElementColSpan;
					colSpanCounter++
				) {
					tableMap[rowIndex + rowSpanCounter][
						columnIndex + colSpanCounter
					] = rows[i].cells[j];
				}
			}

			columnIndex += cellElementColSpan - 1;
		}
	}

	return tableMap;
};
