function createDetailsElement() {
	let detailsElement = document.createElement("details");

	detailsElement.appendChild(document.createElement("summary"));

	detailsElement.appendChild(document.createElement("div"));

	return detailsElement;
}

function createInfoItemElement(name, value, properties) {
	let infoItemElement = document.createElement("div");

	var valueElement;

	if (value == undefined) {
		return infoItemElement;
	}
	else if (value instanceof HTMLElement) {
		valueElement = value;
	}
	else if (Array.isArray(value)) {
		if (value.length == 0) {
			return infoItemElement;
		}

		valueElement = document.createElement("div");

		valueElement.setAttribute("class", "info-item-list");

		var string = "";

		for (var i = 0; i < value.length; i++) {
			string += value[i];
			string += "<br />";

			if (properties && (i < (value.length - 1))) {
				string += "<br />";
			}
		}

		valueElement.innerHTML = string;
	}
	else {
		valueElement = document.createElement("span");

		valueElement.innerHTML = value;
	}

	let nameElement = document.createElement("span");

	nameElement.innerHTML = name + ": ";
	nameElement.setAttribute("class", "info-item-title");

	infoItemElement.appendChild(nameElement);
	infoItemElement.appendChild(valueElement);

	return infoItemElement;
}

function getAxisElement(axis) {
	let detailsElement = createDetailsElement();

	let summaryElement = detailsElement.childNodes[0];

	summaryElement.innerHTML = axis.axis_name;
	summaryElement.setAttribute("class", "level-4");

	let divElement = detailsElement.childNodes[1];

	let ulElement = document.createElement("ul");

	divElement.appendChild(ulElement);

	for (var i = 0; i < axis.test_classes.length; i++) {
		let liElement = document.createElement("li");

		ulElement.appendChild(liElement);

		liElement.appendChild(getTestClassElement(axis.test_classes[i]));
	}

	return detailsElement;
}

function getBatchSummaryElement(batch) {
	let detailsElement = createDetailsElement();

	detailsElement.setAttribute("open", "true");

	let summaryElement = detailsElement.childNodes[0];

	summaryElement.innerHTML = "Batch Summary";
	summaryElement.setAttribute("class", "level-3");

	let infoBoxElement = detailsElement.childNodes[1];

	infoBoxElement.setAttribute("class", "info-box");

	infoBoxElement.appendChild(createInfoItemElement("Job Name", data.job_name));
	infoBoxElement.appendChild(createInfoItemElement("Test Suite Name", data.test_suite_name));
	infoBoxElement.appendChild(createInfoItemElement("Build Profile", data.build_profile));
	infoBoxElement.appendChild(createInfoItemElement("Batch Name", batch.batch_name));

	let jobPropertiesElements = getJobPropertiesElements(batch.job_properties);

	for (var i = 0; i < jobPropertiesElements.length; i++) {
		infoBoxElement.appendChild(jobPropertiesElements[i]);
	}

	infoBoxElement.appendChild(createInfoItemElement("Batch Exclude Globs", batch.exclude_globs));
	infoBoxElement.appendChild(createInfoItemElement("Batch Filter Globs", batch.filter_globs));
	infoBoxElement.appendChild(createInfoItemElement("Batch Include Globs", batch.include_globs));
	infoBoxElement.appendChild(createInfoItemElement("Batch PQL Query", getPQLQueryLines(batch.pql_query)));

	return detailsElement;
}

function getBatchElement(batch) {
	let detailsElement = createDetailsElement();

	let summaryElement = detailsElement.childNodes[0];

	summaryElement.innerHTML = batch.batch_name;
	summaryElement.setAttribute("class", "level-2");

	let divElement = detailsElement.childNodes[1];

	let ulElement = document.createElement("ul");

	divElement.appendChild(ulElement);

	let batchSummaryLiElement = document.createElement("li");

	ulElement.appendChild(batchSummaryLiElement);

	batchSummaryLiElement.appendChild(getBatchSummaryElement(batch));

	let batchSegmentsLiElement = document.createElement("li");

	ulElement.appendChild(batchSegmentsLiElement);

	for (var i = 0; i < batch.segments.length; i++) {
	    batchSegmentsLiElement.append(getSegmentElement(batch.segments[i]));
	}

	return detailsElement;
}

function getJobPropertiesElements(job_properties) {
	if (job_properties == undefined) {
		return [];
	}

	var jobPropertiesElements = [];

	for (let [file, properties] of Object.entries(job_properties)) {
		let lines = [];

		for (let [name, value] of Object.entries(properties)) {
			if (value.includes(",")) {
				value = "\\<br />&nbsp;&nbsp;" + value.replaceAll(",", ",\\<br />&nbsp;&nbsp;");
			}
			else if (name.includes("query")) {
				var pqlQueryLines = getPQLQueryLines(value, 1);

				value = "\\<br />";

				for (var i = 0; i < pqlQueryLines.length; i++) {
					value += pqlQueryLines[i];

					if (i != (pqlQueryLines.length - 1)) {
						value += "\\<br />";
					}
				}
			}

			lines.push(name + "=" + value);
		}

		lines.sort();

		jobPropertiesElements.push(createInfoItemElement("Job Properties (" + file + ")", lines, true));
	}

	return jobPropertiesElements;
}

function getPQLQueryLines(pql_query, balance) {
	if (pql_query == undefined) {
		return undefined;
	}

	if (balance == undefined) {
		balance = 0;
	}

	var lines = [];

	if (!pql_query.includes("(") || !pql_query.includes(")")) {
		var tab = "";

		for (var i = 0; i < balance; i++) {
			tab += "&nbsp;&nbsp;";
		}

		lines.push(tab + pql_query);

		return lines;
	}

	var line = "";

	for (var i = 0; i < pql_query.length; i++) {
		var current = pql_query[i];

		if (current == ')') {
			balance--;
		}

		var next;
		var next_index = i + 1;

		if (next_index != pql_query.length) {
			next = pql_query[next_index]
		}

		line += current;

		if ((current == '(' && next == '(') ||
			(current == ')' && next != ' ') ||
			(current == ' ' && next == '(')) {

			var tab = "";

			for (var j = 0; j < balance; j++) {
				tab += "&nbsp;&nbsp;";
			}

			lines.push(tab + line);

			line = "";
		}

		if (current == '(') {
			balance++;
		}
	}

	return lines;
}

function getSegmentElement(segment) {
	let detailsElement = createDetailsElement();

	let summaryElement = detailsElement.childNodes[0];

	summaryElement.innerHTML = segment.segment_name;
	summaryElement.setAttribute("class", "level-3");

	let divElement = detailsElement.childNodes[1];

	let ulElement = document.createElement("ul");

	divElement.appendChild(ulElement);

    for (var i = 0; i < segment.axes.length; i++) {
        let liElement = document.createElement("li");

        ulElement.appendChild(liElement);

        liElement.appendChild(getAxisElement(segment.axes[i]));
    }

	return detailsElement;
}

function getTestClassElement(test_class) {
	let detailsElement = createDetailsElement();

	let summaryElement = detailsElement.childNodes[0];

	summaryElement.innerHTML = test_class.name;
	summaryElement.setAttribute("class", "level-5");

	let divElement = detailsElement.childNodes[1];

	let ulElement = document.createElement("ul");

	divElement.appendChild(ulElement);

	for (var i = 0; i < test_class.methods.length; i++) {
		let liElement = document.createElement("li");

		liElement.innerHTML = test_class.methods[i].name;

		ulElement.appendChild(liElement);
	}

	return detailsElement;
}

function initialize() {
	updateBatchSummaries();

	updateJobSummary();

	updateSmokeBatchSummaries();
}

function updateBatchSummaries() {
	let batchSummariesElement = document.getElementById("batchSummaries");

	for (var i = 0; i < data.batches.length; i++) {
		batchSummariesElement.appendChild(getBatchElement(data.batches[i]));
	}
}

function updateJobSummary() {
	let infoBoxElement = document.getElementById("jobSummary");

	infoBoxElement.setAttribute("class", "info-box");

	infoBoxElement.appendChild(createInfoItemElement("Job Name", data.job_name));
	infoBoxElement.appendChild(createInfoItemElement("Test Suite Name", data.test_suite_name));
	infoBoxElement.appendChild(createInfoItemElement("Build Profile", data.build_profile));

	if (data.branch != undefined) {
		infoBoxElement.appendChild(createInfoItemElement("Current Branch Name", data.branch.current_branch_name));
		infoBoxElement.appendChild(createInfoItemElement("Current Branch SHA", data.branch.current_branch_sha.substring(0, 7)));

		if (data.job_name.includes("pullrequest")) {
			infoBoxElement.appendChild(createInfoItemElement("Upstream Branch Name", data.branch.upstream_branch_name));
			infoBoxElement.appendChild(createInfoItemElement("Upstream Branch SHA", data.branch.upstream_branch_sha.substring(0, 7)));

			infoBoxElement.appendChild(createInfoItemElement("Merge Branch SHA", data.branch.merge_branch_sha.substring(0, 7)));

			let branch_sha_range = data.branch.merge_branch_sha.substring(0, 7) + "..." + data.branch.current_branch_sha.substring(0, 7);

			infoBoxElement.appendChild(createInfoItemElement("Modified Files (" + branch_sha_range + ")", data.branch.modified_files));
			infoBoxElement.appendChild(createInfoItemElement("Modified Modules (" + branch_sha_range + ")", data.branch.modified_modules));
		}
	}

	let jobPropertiesElements = getJobPropertiesElements(data.job_properties);

	for (var i = 0; i < jobPropertiesElements.length; i++) {
		infoBoxElement.appendChild(jobPropertiesElements[i]);
	}
}

function updateSmokeBatchSummaries() {
	let smokeBatchSummariesElement = document.getElementById("smokeBatchSummaries");

	if ((data.smoke_batches == undefined) || (data.smoke_batches.length == 0)) {
		return;
	}

	for (var i = 0; i < data.smoke_batches.length; i++) {
		smokeBatchSummariesElement.appendChild(getBatchElement(data.smoke_batches[i]));
	}

	let smokeBatchSummaryElement = document.getElementById("smokeBatchSummary");

	smokeBatchSummaryElement.removeAttribute("hidden");
}