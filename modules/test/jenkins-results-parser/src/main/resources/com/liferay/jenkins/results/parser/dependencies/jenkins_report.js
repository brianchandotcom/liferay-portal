function setChildStopWatchRowsVisibility(
	childStopWatchRowNames,
	parentRowId,
	show
) {
	if (childStopWatchRowNames == null) {
		return;
	}

	for (var i = 0; i < childStopWatchRowNames.length; i++) {
		var childStopWatchRowElement = document.getElementById(
			parentRowId + '-' + childStopWatchRowNames[i]
		);

		if (childStopWatchRowElement == null) {
			continue;
		}

		if (show) {
			childStopWatchRowElement.style.display = '';
		} else {
			childStopWatchRowElement.style.display = 'none';
		}

		expanderAnchorElement = document.getElementById(
			parentRowId + '-expander-anchor-' + childStopWatchRowNames[i]
		);

		if (
			!show ||
			(show &&
				expanderAnchorElement != null &&
				expanderAnchorElement.text == '- ')
		) {
			var childStopWatchRowsAttribute = childStopWatchRowElement.getAttribute(
				'child-stopwatch-rows'
			);

			if (childStopWatchRowsAttribute != null) {
				setChildStopWatchRowsVisibility(
					childStopWatchRowsAttribute.split(','),
					parentRowId,
					show
				);
			}
		}
	}
}

function toggleStopWatchRecordExpander(parentRowId, parentStopWatchRecordName) {
	var expanderAnchorElement = document.getElementById(
		parentRowId + '-expander-anchor-' + parentStopWatchRecordName
	);

	var row = document.getElementById(
		parentRowId + '-' + parentStopWatchRecordName
	);

	var childStopWatchRowsAttribute = row.getAttribute('child-stopwatch-rows');

	if (expanderAnchorElement.text == '+ ') {
		expanderAnchorElement.text = '- ';

		if (childStopWatchRowsAttribute != null) {
			setChildStopWatchRowsVisibility(
				childStopWatchRowsAttribute.split(','),
				parentRowId,
				true
			);
		}
	} else {
		expanderAnchorElement.text = '+ ';

		if (childStopWatchRowsAttribute != null) {
			setChildStopWatchRowsVisibility(
				childStopWatchRowsAttribute.split(','),
				parentRowId,
				false
			);
		}
	}

	return false;
}
