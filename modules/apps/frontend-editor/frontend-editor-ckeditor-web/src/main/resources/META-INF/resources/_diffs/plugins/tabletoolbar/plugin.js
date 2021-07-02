(function () {
	function getSelectedCells(selection) {
		const ranges = selection.getRanges();
		const retval = [];
		const database = {};

		function moveOutOfCellGuard(node) {
			// Apply to the first cell only.

			if (retval.length > 0) {
				return;
			}

			// If we are exiting from the first </td>, then the td should definitely be
			// included.

			if (
				node.type == CKEDITOR.NODE_ELEMENT &&
				cellNodeRegex.test(node.getName()) &&
				!node.getCustomData("selected_cell")
			) {
				CKEDITOR.dom.element.setMarker(database, node, "selected_cell", true);
				retval.push(node);
			}
		}

		for (let i = 0; i < ranges.length; i++) {
			const range = ranges[i];

			if (range.collapsed) {
				// Walker does not handle collapsed ranges yet - fall back to old API.

				const startNode = range.getCommonAncestor();
				const nearestCell =
					startNode.getAscendant("td", true) ||
					startNode.getAscendant("th", true);
				if (nearestCell) {
					retval.push(nearestCell);
				}
			} else {
				const walker = new CKEDITOR.dom.walker(range);
				let node;
				walker.guard = moveOutOfCellGuard;

				while ((node = walker.next())) {
					// If may be possible for us to have a range like this:
					// <td>^1</td><td>^2</td>
					// The 2nd td shouldn't be included.
					//
					// So we have to take care to include a td we've entered only when we've
					// walked into its children.

					if (
						node.type != CKEDITOR.NODE_ELEMENT ||
						!node.is(CKEDITOR.dtd.table)
					) {
						const parent =
							node.getAscendant("td", true) || node.getAscendant("th", true);
						if (parent && !parent.getCustomData("selected_cell")) {
							CKEDITOR.dom.element.setMarker(
								database,
								parent,
								"selected_cell",
								true
							);
							retval.push(parent);
						}
					}
				}
			}
		}

		CKEDITOR.dom.element.clearAllMarkers(database);

		return retval;
	}

	function getFocusElementAfterDelCells(cellsToDelete) {
		let i = 0;

		const last = cellsToDelete.length - 1;

		const database = {};

		let cell;

		let focusedCell;

		let tr;

		while ((cell = cellsToDelete[i++])) {
			CKEDITOR.dom.element.setMarker(database, cell, "delete_cell", true);
		}

		// 1.first we check left or right side focusable cell row by row;

		i = 0;
		while ((cell = cellsToDelete[i++])) {
			if (
				((focusedCell = cell.getPrevious()) &&
					!focusedCell.getCustomData("delete_cell")) ||
				((focusedCell = cell.getNext()) &&
					!focusedCell.getCustomData("delete_cell"))
			) {
				CKEDITOR.dom.element.clearAllMarkers(database);

				return focusedCell;
			}
		}

		CKEDITOR.dom.element.clearAllMarkers(database);

		// 2. then we check the toppest row (outside the selection area square) focusable cell

		tr = cellsToDelete[0].getParent();
		if ((tr = tr.getPrevious())) {
			return tr.getLast();
		}

		// 3. last we check the lowerest  row focusable cell

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

		const table = firstCell.getAscendant("table");

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

		const newRow = doc.createElement("tr");
		for (let i = 0; cloneRow[i] && i < width; i++) {
			let cell;

			// Check whether there's a spanning row here, do not break it.

			if (cloneRow[i].rowSpan > 1 && nextRow && cloneRow[i] == nextRow[i]) {
				cell = cloneRow[i];
				cell.rowSpan += 1;
			} else {
				cell = new CKEDITOR.dom.element(cloneRow[i]).clone();
				cell.removeAttribute("rowSpan");
				cell.appendBogus();
				newRow.append(cell);
				cell = cell.$;
			}

			i += cell.colSpan - 1;
		}

		if (insertBefore) {
			newRow.insertBefore(row);
		} else {
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

			const table = firstCell.getAscendant("table");

			const map = CKEDITOR.tools.buildTableMap(table);

			const startRow = cells[0].getParent();

			const startRowIndex = startRow.$.rowIndex;

			const lastCell = cells[cells.length - 1];

			const endRowIndex =
				lastCell.getParent().$.rowIndex + lastCell.$.rowSpan - 1;

			const rowsToDelete = [];

			// Delete cell or reduce cell spans by checking through the table map.

			for (let i = startRowIndex; i <= endRowIndex; i++) {
				const mapRow = map[i];

				const row = new CKEDITOR.dom.element(table.$.rows[i]);

				for (let j = 0; j < mapRow.length; j++) {
					const cell = new CKEDITOR.dom.element(mapRow[j]);

					const cellRowIndex = cell.getParent().$.rowIndex;

					if (cell.$.rowSpan == 1) {
						cell.remove();
					}

					// Row spanned cell.
					else {
						// Span row of the cell, reduce spanning.

						cell.$.rowSpan -= 1;

						// Root row of the cell, root cell to next row.

						if (cellRowIndex == i) {
							const nextMapRow = map[i + 1];
							if (nextMapRow[j - 1]) {
								cell.insertAfter(new CKEDITOR.dom.element(nextMapRow[j - 1]));
							} else {
								new CKEDITOR.dom.element(table.$.rows[i + 1]).append(cell, 1);
							}
						}
					}

					j += cell.$.colSpan - 1;
				}

				rowsToDelete.push(row);
			}

			const rows = table.$.rows;

			// Where to put the cursor after rows been deleted?
			// 1. Into next sibling row if any;
			// 2. Into previous sibling row if any;
			// 3. Into table's parent element if it's the very last row.

			const cursorPosition = new CKEDITOR.dom.element(
				rows[endRowIndex + 1] ||
					(startRowIndex > 0 ? rows[startRowIndex - 1] : null) ||
					table.$.parentNode
			);

			for (let i = rowsToDelete.length; i >= 0; i--) {
				deleteRows(rowsToDelete[i]);
			}

			return cursorPosition;
		} else if (selectionOrRow instanceof CKEDITOR.dom.element) {
			const table = selectionOrRow.getAscendant("table");

			if (table.$.rows.length == 1) {
				table.remove();
			} else {
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
		let retval = isStart ? Infinity : 0;
		for (let i = 0; i < cells.length; i++) {
			const colIndex = getCellColIndex(cells[i], isStart);
			if (isStart ? colIndex < retval : colIndex > retval) {
				retval = colIndex;
			}
		}

		return retval;
	}

	function insertColumn(editor, insertBefore) {
		const selection = editor.getSelection();

		const cells = getSelectedCells(selection);

		const firstCell = cells[0];

		const table = firstCell.getAscendant("table");

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

			// Check whether there's a spanning column here, do not break it.

			if (cloneCol[i].colSpan > 1 && nextCol[i] == cloneCol[i]) {
				cell = cloneCol[i];
				cell.colSpan += 1;
			} else {
				cell = new CKEDITOR.dom.element(cloneCol[i]).clone();
				cell.removeAttribute("colSpan");
				cell.appendBogus();
				cell[insertBefore ? "insertBefore" : "insertAfter"].call(
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

		const table = firstCell.getAscendant("table");

		const map = CKEDITOR.tools.buildTableMap(table);

		let startColIndex;

		let endColIndex;

		const rowsToDelete = [];

		let rows;

		// Figure out selected cells' column indices.

		for (let i = 0, rows = map.length; i < rows; i++) {
			// eslint-disable-next-line sort-vars
			for (let j = 0, cols = map[i].length; j < cols; j++) {
				if (map[i][j] == firstCell.$) {
					startColIndex = j;
				}
				if (map[i][j] == lastCell.$) {
					endColIndex = j;
				}
			}
		}

		// Delete cell or reduce cell spans by checking through the table map.

		for (let i = startColIndex; i <= endColIndex; i++) {
			for (let j = 0; j < map.length; j++) {
				const mapRow = map[j];

				const row = new CKEDITOR.dom.element(table.$.rows[j]);

				const cell = new CKEDITOR.dom.element(mapRow[i]);

				if (cell.$) {
					if (cell.$.colSpan == 1) {
						cell.remove();
					}

					// Reduce the col spans.
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

		// Where to put the cursor after columns been deleted?
		// 1. Into next cell of the first row if any;
		// 2. Into previous cell of the first row if any;
		// 3. Into table's parent element;

		const cursorPosition = new CKEDITOR.dom.element(
			firstRowCells[startColIndex] ||
				(startColIndex ? firstRowCells[startColIndex - 1] : table.$.parentNode)
		);

		// Delete table rows only if all columns are gone (do not remove empty row).

		if (rowsToDelete.length == rows) {
			table.remove();
		}

		return cursorPosition;
	}

	function insertCell(selection, insertBefore) {
		const startElement = selection.getStartElement();
		const cell =
			startElement.getAscendant("td", 1) || startElement.getAscendant("th", 1);

		if (!cell) {
			return;
		}

		// Create the new cell element to be added.

		const newCell = cell.clone();
		newCell.appendBogus();

		if (insertBefore) {
			newCell.insertBefore(cell);
		} else {
			newCell.insertAfter(cell);
		}
	}

	function deleteCells(selectionOrCell) {
		if (selectionOrCell instanceof CKEDITOR.dom.selection) {
			const cellsToDelete = getSelectedCells(selectionOrCell);
			const table = cellsToDelete[0] && cellsToDelete[0].getAscendant("table");
			const cellToFocus = getFocusElementAfterDelCells(cellsToDelete);

			for (let i = cellsToDelete.length - 1; i >= 0; i--) {
				deleteCells(cellsToDelete[i]);
			}

			if (cellToFocus) {
				placeCursorInCell(cellToFocus, true);
			} else if (table) {
				table.remove();
			}
		} else if (selectionOrCell instanceof CKEDITOR.dom.element) {
			const tr = selectionOrCell.getParent();
			if (tr.getChildCount() == 1) {
				tr.remove();
			} else {
				selectionOrCell.remove();
			}
		}
	}

	// Remove filler at end and empty spaces around the cell content.

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

		// Fixing "Unspecified error" thrown in IE10 by resetting
		// selection the dirty and shameful way (#10308).
		// We can not apply this hack to IE8 because
		// it causes error (#11058).

		if (CKEDITOR.env.ie && CKEDITOR.env.version == 10) {
			docOuter.focus();
			docInner.focus();
		}

		const range = new CKEDITOR.dom.range(docInner);
		if (!range["moveToElementEdit" + (placeAtEnd ? "End" : "Start")](cell)) {
			range.selectNodeContents(cell);
			range.collapse(placeAtEnd ? false : true);
		}
		range.select(true);
	}

	function cellInRow(tableMap, rowIndex, cell) {
		const oRow = tableMap[rowIndex];
		if (typeof cell == "undefined") {
			return oRow;
		}

		for (let c = 0; oRow && c < oRow.length; c++) {
			if (cell.is && oRow[c] == cell.$) {
				return c;
			} else if (c == cell) {
				return new CKEDITOR.dom.element(oRow[c]);
			}
		}

		return cell.is ? -1 : null;
	}

	function cellInCol(tableMap, colIndex) {
		const oCol = [];
		for (let r = 0; r < tableMap.length; r++) {
			const row = tableMap[r];
			oCol.push(row[colIndex]);

			// Avoid adding duplicate cells.

			if (row[colIndex].rowSpan > 1) {
				r += row[colIndex].rowSpan - 1;
			}
		}

		return oCol;
	}

	function mergeCells(selection, mergeDirection, isDetect) {
		const cells = getSelectedCells(selection);

		// Invalid merge request if:
		// 1. In batch mode despite that less than two selected.
		// 2. In solo mode while not exactly only one selected.
		// 3. Cells distributed in different table groups (e.g. from both thead and tbody).

		let commonAncestor;
		if (
			(mergeDirection ? cells.length != 1 : cells.length < 2) ||
			((commonAncestor = selection.getCommonAncestor()) &&
				commonAncestor.type == CKEDITOR.NODE_ELEMENT &&
				commonAncestor.is("table"))
		) {
			return false;
		}

		let cell;

		const firstCell = cells[0];

		const table = firstCell.getAscendant("table");

		const map = CKEDITOR.tools.buildTableMap(table);

		const mapHeight = map.length;

		const mapWidth = map[0].length;

		const startRow = firstCell.getParent().$.rowIndex;

		const startColumn = cellInRow(map, startRow, firstCell);

		if (mergeDirection) {
			let targetCell;
			try {
				const rowspan = parseInt(firstCell.getAttribute("rowspan"), 10) || 1;
				const colspan = parseInt(firstCell.getAttribute("colspan"), 10) || 1;

				targetCell =
					map[
						mergeDirection == "up"
							? startRow - rowspan
							: mergeDirection == "down"
							? startRow + rowspan
							: startRow
					][
						mergeDirection == "left"
							? startColumn - colspan
							: mergeDirection == "right"
							? startColumn + colspan
							: startColumn
					];
			} catch (er) {
				return false;
			}

			// 1. No cell could be merged.
			// 2. Same cell actually.

			if (!targetCell || firstCell.$ == targetCell) {
				return false;
			}

			// Sort in map order regardless of the DOM sequence.

			cells[
				mergeDirection == "up" || mergeDirection == "left" ? "unshift" : "push"
			](new CKEDITOR.dom.element(targetCell));
		}

		// Start from here are merging way ignorance (merge up/right, batch merge).

		const doc = firstCell.getDocument();

		let lastRowIndex = startRow;

		let totalRowSpan = 0;

		let totalColSpan = 0;

		// Use a documentFragment as buffer when appending cell contents.

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

			// Accumulated the actual places taken by all selected cells.

			dimension += colSpan * rowSpan;

			// Accumulated the maximum virtual spans from column and row.

			totalColSpan = Math.max(totalColSpan, colIndex - startColumn + colSpan);
			totalRowSpan = Math.max(totalRowSpan, rowIndex - startRow + rowSpan);

			if (!isDetect) {
				// Trim all cell fillers and check to remove empty cells.

				if ((trimCell(cell), cell.getChildren().count())) {
					// Merge vertically cells as two separated paragraphs.

					if (
						rowIndex != lastRowIndex &&
						cellFirstChild &&
						!(
							cellFirstChild.isBlockBoundary &&
							cellFirstChild.isBlockBoundary({ br: 1 })
						)
					) {
						const last = frag.getLast(CKEDITOR.dom.walker.whitespaces(true));
						if (last && !(last.is && last.is("br"))) {
							frag.append("br");
						}
					}

					cell.moveChildren(frag);
				}
				if (i) {
					cell.remove();
				} else {
					cell.setHtml("");
				}
			}
			lastRowIndex = rowIndex;
		}

		if (!isDetect) {
			frag.moveChildren(firstCell);

			firstCell.appendBogus();

			if (totalColSpan >= mapWidth) {
				firstCell.removeAttribute("rowSpan");
			} else {
				firstCell.$.rowSpan = totalRowSpan;
			}

			if (totalRowSpan >= mapHeight) {
				firstCell.removeAttribute("colSpan");
			} else {
				firstCell.$.colSpan = totalColSpan;
			}

			// Swip empty <tr> left at the end of table due to the merging.

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

		// Be able to merge cells only if actual dimension of selected
		// cells equals to the caculated rectangle.
		else {
			return totalRowSpan * totalColSpan == dimension;
		}
	}

	function verticalSplitCell(selection, isDetect) {
		const cells = getSelectedCells(selection);
		if (cells.length > 1) {
			return false;
		} else if (isDetect) {
			return true;
		}

		const cell = cells[0];

		const tr = cell.getParent();

		const table = tr.getAscendant("table");

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
			const newCellTr = new CKEDITOR.dom.element(table.$.rows[newRowIndex]);

			const newCellRow = cellInRow(map, newRowIndex);

			let candidateCell;

			newCell = cell.clone();

			// Figure out where to insert the new cell by checking the vitual row.

			for (let c = 0; c < newCellRow.length; c++) {
				candidateCell = newCellRow[c];

				// Catch first cell actually following the column.

				if (candidateCell.parentNode == newCellTr.$ && c > colIndex) {
					newCell.insertBefore(new CKEDITOR.dom.element(candidateCell));
					break;
				} else {
					candidateCell = null;
				}
			}

			// The destination row is empty, append at will.

			if (!candidateCell) {
				newCellTr.append(newCell);
			}
		} else {
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
			cell.removeAttribute("rowSpan");
		}
		if (newCellRowSpan == 1) {
			newCell.removeAttribute("rowSpan");
		}

		return newCell;
	}

	function horizontalSplitCell(selection, isDetect) {
		const cells = getSelectedCells(selection);
		if (cells.length > 1) {
			return false;
		} else if (isDetect) {
			return true;
		}

		const cell = cells[0];

		const tr = cell.getParent();

		const table = tr.getAscendant("table");

		const map = CKEDITOR.tools.buildTableMap(table);

		const rowIndex = tr.$.rowIndex;

		const colIndex = cellInRow(map, rowIndex, cell);

		const colSpan = cell.$.colSpan;

		let newColSpan;

		let newCellColSpan;

		if (colSpan > 1) {
			newColSpan = Math.ceil(colSpan / 2);
			newCellColSpan = Math.floor(colSpan / 2);
		} else {
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
			cell.removeAttribute("colSpan");
		}
		if (newCellColSpan == 1) {
			newCell.removeAttribute("colSpan");
		}

		return newCell;
	}

	CKEDITOR.plugins.add("tabletoolbar", {
		requires: "panelbutton,floatpanel",
		icons: "add-row, add-column, add-cell",
		init: function (editor) {
			function createDef(def) {
				return CKEDITOR.tools.extend(def || {}, {
					contextSensitive: 1,
					refresh(editor, path) {
						this.setState(
							path.contains({ td: 1, th: 1 }, 1)
								? CKEDITOR.TRISTATE_OFF
								: CKEDITOR.TRISTATE_DISABLED
						);
					},
				});
			}

			function addCmd(config, def, clickFn) {
				var commandName = config.commandName || "";
				var editorFocus = config.editorFocus || 0;
				var name = config.name;
				var label = config.label || "";
				var title = config.title || "";
				var requiredContent = config.requiredContent || "";
				var panel = config.panel || {};

				var cmd = editor.getCommand(commandName);

				if (cmd) {
					return;
				}

				cmd = editor.addCommand(commandName, def);
				editor.addFeature(cmd);

				var panelId = CKEDITOR.tools.getNextId() + "_panel";
				config.panelId = panelId;

				var panelBlock;

				editor.ui.add(name, CKEDITOR.UI_PANELBUTTON, {
					label: label,
					title: title,
					command: commandName,
					editorFocus: editorFocus,
					requiredContent: requiredContent,
					panel: panel,
					onBlock: function (panel, block) {
						panelBlock = block;
						block.autosize = true;
						block.element.setHtml(renderPanel(config, clickFn));
					},
					onOpen: function () {
						return panelBlock;
					},
				});
			}

			function renderPanel(config, clickFn) {
				var output = [];

				var box = new ListBox(editor, config, clickFn);

				output.push(box.getHtml());

				return output.join("");
			}

			function rowDelete() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("rowDelete");
				});
			}

			function rowInsertBefore() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("rowInsertBefore");
				});
			}

			function rowInsertAfter() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("rowInsertAfter");
				});
			}

			function columnDelete() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("columnDelete");
				});
			}

			function columnInsertBefore() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("columnInsertBefore");
				});
			}

			function columnInsertAfter() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("columnInsertAfter");
				});
			}

			function cellDelete() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellDelete");
				});
			}

			function cellMerge() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellMerge");
				});
			}

			function cellMergeRight() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellMergeRight");
				});
			}

			function cellMergeDown() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellMergeDown");
				});
			}

			function cellVerticalSplit() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellVerticalSplit");
				});
			}

			function cellHorizontalSplit() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellHorizontalSplit");
				});
			}

			function cellInsertBefore() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellInsertBefore");
				});
			}

			function cellInsertAfter() {
				return CKEDITOR.tools.addFunction(function () {
					editor.execCommand("cellInsertAfter");
				});
			}

			addCmd(
				{
					commandName: "tableRow",
					editorFocus: 0,
					label: "tableRow",
					name: "TableRow",
					panel: {
						css: CKEDITOR.skin.getPath("editor"),
						attributes: {
							"aria-label": "tableRow",
							role: "listbox",
						},
						links: [
							{
								commandName: "rowDelete",
								clickFn: rowDelete(),
							},
							{
								commandName: "rowInsertBefore",
								clickFn: rowInsertBefore(),
							},
							{
								commandName: "rowInsertAfter",
								clickFn: rowInsertAfter(),
							},
						],
					},
					requiredContent: "table",
					title: "tableRow",
				},
				createDef({
					requiredContent: "table",
				})
			);

			addCmd(
				{
					commandName: "rowDelete",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						const selection = editor.getSelection();
						placeCursorInCell(deleteRows(selection));
					},
				})
			);

			addCmd(
				{
					commandName: "rowInsertBefore",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						insertRow(editor, true);
					},
				})
			);

			addCmd(
				{
					commandName: "rowInsertAfter",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						insertRow(editor);
					},
				})
			);

			addCmd(
				{
					commandName: "tableColumn",
					editorFocus: 0,
					label: "tableColumn",
					name: "TableColumn",
					panel: {
						css: CKEDITOR.skin.getPath("editor"),
						attributes: {
							"aria-label": "tableColumn",
							role: "listbox",
						},
						links: [
							{
								commandName: "columnDelete",
								clickFn: columnDelete(),
							},
							{
								commandName: "columnInsertBefore",
								clickFn: columnInsertBefore(),
							},
							{
								commandName: "columnInsertAfter",
								clickFn: columnInsertAfter(),
							},
						],
					},
					requiredContent: "table",
					title: "tableColumn",
				},
				createDef({
					requiredContent: "table",
				})
			);

			addCmd(
				{
					commandName: "columnDelete",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						const selection = editor.getSelection();
						const element = deleteColumns(selection);
						if (element) {
							placeCursorInCell(element, true);
						}
					},
				})
			);

			addCmd(
				{
					commandName: "columnInsertBefore",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						insertColumn(editor, true);
					},
				})
			);

			addCmd(
				{
					commandName: "columnInsertAfter",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						insertColumn(editor);
					},
				})
			);

			addCmd(
				{
					commandName: "tableCell",
					editorFocus: 0,
					label: "tableCell",
					name: "TableCell",
					panel: {
						css: CKEDITOR.skin.getPath("editor"),
						attributes: {
							"aria-label": "tableColumn",
							role: "listbox",
						},
						links: [
							{
								commandName: "cellDelete",
								clickFn: cellDelete(),
							},
							{
								commandName: "cellMerge",
								clickFn: cellMerge(),
							},
							{
								commandName: "cellMergeRight",
								clickFn: cellMergeRight(),
							},
							{
								commandName: "cellMergeDown",
								clickFn: cellMergeDown(),
							},
							{
								commandName: "cellVerticalSplit",
								clickFn: cellVerticalSplit(),
							},
							{
								commandName: "cellHorizontalSplit",
								clickFn: cellHorizontalSplit(),
							},
							{
								commandName: "cellInsertBefore",
								clickFn: cellInsertBefore(),
							},
							{
								commandName: "cellInsertAfter",
								clickFn: cellInsertAfter(),
							},
						],
					},
					requiredContent: "table",
					title: "tableCell",
				},
				createDef({
					requiredContent: "table",
				})
			);

			addCmd(
				{
					commandName: "cellDelete",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						const selection = editor.getSelection();
						deleteCells(selection);
					},
				})
			);

			addCmd(
				{
					commandName: "cellMerge",
				},
				createDef({
					allowedContent: "td[colspan,rowspan]",
					requiredContent: "td[colspan,rowspan]",
					exec(editor) {
						placeCursorInCell(mergeCells(editor.getSelection()), true);
					},
				})
			);

			addCmd(
				{
					commandName: "cellMergeRight",
				},
				createDef({
					allowedContent: "td[colspan]",
					requiredContent: "td[colspan]",
					exec(editor) {
						placeCursorInCell(mergeCells(editor.getSelection(), "right"), true);
					},
				})
			);

			addCmd(
				{
					commandName: "cellMergeDown",
				},
				createDef({
					allowedContent: "td[rowspan]",
					requiredContent: "td[rowspan]",
					exec(editor) {
						placeCursorInCell(mergeCells(editor.getSelection(), "down"), true);
					},
				})
			);

			addCmd(
				{
					commandName: "cellVerticalSplit",
				},
				createDef({
					allowedContent: "td[rowspan]",
					requiredContent: "td[rowspan]",
					exec(editor) {
						placeCursorInCell(verticalSplitCell(editor.getSelection()));
					},
				})
			);

			addCmd(
				{
					commandName: "cellHorizontalSplit",
				},
				createDef({
					allowedContent: "td[colspan]",
					requiredContent: "td[colspan]",
					exec(editor) {
						placeCursorInCell(horizontalSplitCell(editor.getSelection()));
					},
				})
			);

			addCmd(
				{
					commandName: "cellInsertBefore",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						const selection = editor.getSelection();
						insertCell(selection, true);
					},
				})
			);

			addCmd(
				{
					commandName: "cellInsertAfter",
				},
				createDef({
					requiredContent: "table",
					exec(editor) {
						const selection = editor.getSelection();
						insertCell(selection);
					},
				})
			);
		},
		getSelectedCells,
	});

	ListBox = CKEDITOR.tools.createClass({
		$: function (editor, config, clickFn) {
			this.$ = new CKEDITOR.dom.element("div");

			this.clickFn = clickFn;

			this.setHtml(config);
		},
		proto: {
			getElement: function () {
				return this.$;
			},

			getHtml: function () {
				return this.getElement().getOuterHtml();
			},

			setHtml: function (config) {
				this.getElement().setHtml(
					"<ul class='listbox' id='" +
						config.panelId +
						"' role='listbox'>" +
						"<li role='option'>" +
						"<button aria-label='' class='container toolbar-element' onClick='CKEDITOR.tools.callFunction(" +
						config.panel.links[0].clickFn +
						", this" +
						"); return false;' tablIndex=''>" +
						config.panel.links[0].commandName +
						"</button>" +
						"</li>" +
						"<li role='option'>" +
						"<button aria-label='' class='container toolbar-element' onClick='CKEDITOR.tools.callFunction(" +
						config.panel.links[1].clickFn +
						", this" +
						"); return false;' tablIndex=''>" +
						config.panel.links[1].commandName +
						"</button>" +
						"</li>" +
						"<li role='option'>" +
						"<button aria-label='' class='container toolbar-element' onClick='CKEDITOR.tools.callFunction(" +
						config.panel.links[2].clickFn +
						", this" +
						"); return false;' tablIndex=''>" +
						config.panel.links[2].commandName +
						"</button>" +
						"</li>" +
						"</ul>"
				);
			},
		},
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
	var aRows = table.$.rows;

	// Row and Column counters.

	var r = -1;

	var aMap = [];

	for (var i = 0; i < aRows.length; i++) {
		r++;
		if (!aMap[r]) {
			aMap[r] = [];
		}

		var c = -1;

		for (var j = 0; j < aRows[i].cells.length; j++) {
			var oCell = aRows[i].cells[j];

			c++;
			while (aMap[r][c]) {
				c++;
			}

			var iColSpan = isNaN(oCell.colSpan) ? 1 : oCell.colSpan;
			var iRowSpan = isNaN(oCell.rowSpan) ? 1 : oCell.rowSpan;

			for (var rs = 0; rs < iRowSpan; rs++) {
				if (!aMap[r + rs]) {
					aMap[r + rs] = [];
				}

				for (var cs = 0; cs < iColSpan; cs++) {
					aMap[r + rs][c + cs] = aRows[i].cells[j];
				}
			}

			c += iColSpan - 1;
		}
	}

	return aMap;
};
