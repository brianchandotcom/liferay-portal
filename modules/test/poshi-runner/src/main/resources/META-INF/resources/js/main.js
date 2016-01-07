function addChildLoggerElement(childLoggerAttributes, extraAttributes) {
	var childNode = document.createElement(childLoggerAttributes.name);
	var parentNode = document.getElementById(childLoggerAttributes.parentId);

	if (childNode) {
		childNode.setAttribute('class', childLoggerAttributes.cssClass);
		childNode.setAttribute('id', childLoggerAttributes.id);

		childNode.innerHTML = childLoggerAttributes.innerHTML;

		for (var attribute in extraAttributes) {
			childNode.setAttribute(attribute, extraAttributes[attribute]);
		}
		if (parentNode) {
			parentNode.appendChild(childNode);
		}
	}
}

function getClassName(id) {
	var node = document.getElementById(id);

	var val = null;

	if (node) {
		val = node.getAttribute('class');
	}

	return val;
}

function getName(id) {
	var node = document.getElementById(id);

	var val = null;

	if (node) {
		val = node.nodeName;
	}

	return val;
}

function getText(id) {
	var node = document.getElementById(id);

	var val = null;

	if (node) {
		val = node.innerHTML;
	}

	return val;
}

function isWrittenToLogger(id) {
	var node = document.getElementById(id);

	var val = false;

	if (node) {
		val = true;
	}

	return val;
}

function macroHover(node, enter) {
	var lineGroup = node.parentNode;

	if (enter) {
		lineGroup.classList.add('hover');
	}
	else {
		lineGroup.classList.remove('hover');
	}
}

function setAttribute(id, attrName, attrValue) {
	var node = document.getElementById(id);

	if (node) {
		node.setAttribute(attrName, attrValue);
	}
}

function setClassName(id, className) {
	var node = document.getElementById(id);

	if (node) {
		node.setAttribute('class', className);
	}
}

function setName(id, name) {
	var oldNode = document.getElementById(id);

	if (oldNode) {
		var newNode = document.createElement(name);

		newNode.innerHTML = oldNode.innerHTML;

		var oldNodeClassName = oldNode.getAttribute('class');
		var oldNodeId = oldNode.getAttribute('id');

		newNode.setAttribute('class', oldNodeClassName);
		newNode.setAttribute('id', oldNodeId);

		oldNode.parentNode.insertBefore(newNode, oldNode.nextSibling);

		var parentNode = oldNode.parentNode;

		parentNode.removeChild(oldNode);
	}
}

function setText(id, text) {
	var node = document.getElementById(id);

	if (node) {
		node.innerHTML = text;
	}
}

var loggerInterface = YUI().use(
	'liferay-qa-poshi-logger',
	function(Y) {
		var logger = new Y.PoshiLogger(
			{
				contentBox: '.poshi-logger',
				sidebar: '.sidebar',
				xmlLog: '.xml-log'
			}
		).render();

		Y.on(
			'command-complete',
			logger.handleCommandCompleted,
			logger
		);

		Y.on(
			'line-trigger',
			logger.handleLineTrigger,
			logger
		);

		Y.on(
			'pause-trigger',
			logger.handlePauseBtn,
			logger
		);
	}
);