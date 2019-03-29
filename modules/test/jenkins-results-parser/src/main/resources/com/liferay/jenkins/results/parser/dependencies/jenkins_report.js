function setChildStopWatchRowsVisibility(childRowNames, namespace, show) {
	if (childRowNames == null) {
		return;
	}

	for (var i = 0; i < childRowNames.length; i++) {
		var rowElement = document.getElementById(
			namespace + "-" + childRowNames[i]);

		if (rowElement == null) {
			continue;
		}

		if (show) {
			rowElement.style.display = "";
		}
		else {
			rowElement.style.display = "none";
		}

		anchorElement = document.getElementById(
			namespace + "-expander-anchor-" + childRowNames[i]);

		if (!show ||
			(show && (anchorElement != null) &&
				anchorElement.text == "- ")) {

			var childrenAttribute = rowElement.getAttribute(
				"child-stopwatch-rows");

			if (childrenAttribute != null) {
				setChildStopWatchRowsVisibility(
					childrenAttribute.split(","), namespace, show);
			}
		}
	}
}

function toggleStopwatchRecordExpander(namespace, parentStopwatchRecordName) {
	var anchorElement = document.getElementById(
		namespace + "-expander-anchor-" + parentStopwatchRecordName);

	var row = document.getElementById(
		namespace + "-" + parentStopwatchRecordName);

	var childrenAttribute = row.getAttribute("child-stopwatch-rows");

	if (anchorElement.text == "+ ") {
		anchorElement.text = "- ";

		if (childrenAttribute != null) {
			setChildStopWatchRowsVisibility(
				childrenAttribute.split(","), namespace, true);
		}
	}
	else {
		anchorElement.text = "+ ";

		if (childrenAttribute != null) {
			setChildStopWatchRowsVisibility(
				childrenAttribute.split(","), namespace, false);
		}
	}

	return false;
}