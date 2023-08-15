let executeUsageDataTable = document.getElementById("execute-usage-data-table");

createTable(executeUsageDataTable, executeUsageData);

addDateText(document.getElementById("execute-usage-data-date"), executeUsageDataGeneratedDate);

Sortable.init();

window.onload = function () {
	var statusChangesRowHeader = getElementByXpath("//th[contains(.,'Status Changes')]");

	triggerEvent(statusChangesRowHeader, 'click');
}

function addDateText(element, date) {
	let dateText = document.createTextNode("Generated " + timeago.format(date) + " on " + date)

	element.appendChild(dateText);
}

function createTable(table, tableData) {
	let tbody = table.createTBody();

	tableData.forEach((rowData, index) => {
		if (index == 0) {
			let thead = table.createTHead();

			let row = thead.insertRow();

			rowData.forEach((columnHeader) => {
				let th = document.createElement("th");

				th.appendChild(document.createTextNode(columnHeader));

				row.appendChild(th);
			});
		}
		else {
			let row = tbody.insertRow();

			rowData.forEach((cellData, columnIndex) => {
				let cell = row.insertCell();

				if (Array.isArray(cellData)) {
					let filePaths = "";
					cellData.forEach((fileData) => {
						filePaths = filePaths.concat(fileData, ", ");
					});
					filePaths = filePaths.slice(0, filePaths.length-2)
					let divElement = document.createElement("div");

					let spanElement = document.createElement("span");

					cell.classList.add("col-2");
					cell.classList.add("truncate");
					cell.classList.add("wrap");

					spanElement.appendChild(document.createTextNode(filePaths));

						divElement.appendChild(spanElement);

						divElement.setAttribute("data-value", filePaths);

						node = divElement;

						cell.appendChild(node);
				}
				else {
					let node = null;

					if (typeof cellData === "string" || cellData instanceof String) {
						let divElement = document.createElement("div");

						let spanElement = document.createElement("span");

						if (columnIndex == 0) {
							cell.classList.add("col-1");
						} else if (columnIndex == 1){
							cell.classList.add("col-2");
							cell.classList.add("wrap");
						}

						spanElement.appendChild(document.createTextNode(cellData));

						divElement.appendChild(spanElement);

						divElement.setAttribute("data-value", cellData);

						node = divElement;
					}
					else {
						node = document.createTextNode(cellData);
					}

					cell.appendChild(node);
				}
			});
		}
	});
}

function getElementByXpath(path) {
	return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

function triggerEvent(element, eventName) {
	element.dispatchEvent(new Event(eventName));
}