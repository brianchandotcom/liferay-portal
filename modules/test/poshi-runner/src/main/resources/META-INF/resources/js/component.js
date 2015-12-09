YUI.add(
	'liferay-qa-poshi-logger',
	function(A) {
		var Lang = A.Lang;

		var ATTR_DATA_BUTTON_LINK_ID = 'btnLinkId';

		var ATTR_DATA_ERROR_LINK_ID = 'errorLinkId';

		var ATTR_DATA_FUNCTION_LINK_ID = 'functionLinkId';

		var ATTR_DATA_LOG_ID = 'logId';

		var CSS_COLLAPSE = 'collapse';

		var CSS_CURRENT_SCOPE = 'current-scope';

		var CSS_FAIL = 'fail';

		var CSS_HIDDEN = 'hidden';

		var CSS_TOGGLE = 'toggle';

		var CSS_TRANSITIONING = 'transitioning';

		var STR_BLANK = '';

		var STR_COMMAND_LOG_ID = 'commandLogId';

		var STR_COMMAND_LOG_SCOPE = 'commandLogScope';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_CURRENT_SCOPE = 'currentScope';

		var STR_DOT = '.';

		var STR_FAILS = 'fails';

		var STR_HEIGHT = 'height';

		var STR_RUNNING = 'running';

		var STR_SIDEBAR = 'sidebar';

		var STR_SRC = 'src';

		var STR_STATUS = 'status';

		var STR_XML_LOG = 'xmlLog';

		var SELECTOR_FAIL = STR_DOT + CSS_FAIL;

		var TPL_ERROR_BUTTONS = '<button class="btn {cssClass}" data-errorlinkid="{linkId}" onclick="loggerInterface.handleErrorBtns">' +
				'<div class="btn-content"></div>' +
			'</button>';

		var TPL_PARAMETER = '<li class="{cssClass}">{parameter}</li>';

		var WIN = A.getWin();

		var PoshiLogger = A.Component.create(
			{
				ATTRS: {
					commandLogId: {
						value: null
					},

					commandLogScope: {
						value: new A.NodeList()
					},

					currentScope: {
						setter: A.one
					},

					fails: {
						setter: function() {
							var instance = this;

							var xmlLog = instance.get(STR_XML_LOG);

							return xmlLog.all(SELECTOR_FAIL);
						}
					},

					running: {
						value: null
					},

					sidebar: {
						setter: A.one
					},

					status: {
						validator: Lang.isArray,
						value: ['fail', 'pass', 'pending']
					},

					xmlLog: {
						setter: A.one
					}
				},

				NAME: 'poshilogger',

				prototype: {
					renderUI: function() {
						var instance = this;

						var sidebar = instance.get(STR_SIDEBAR);
						var xmlLog = instance.get(STR_XML_LOG);

						xmlLog.toggleClass(STR_RUNNING);

						var commandLog = sidebar.one('.command-log');

						instance._toggleCommandLog(commandLog);

						if (!xmlLog.hasClass(STR_RUNNING)) {
							instance._minimizeSidebar();
						}
					},

					bindUI: function() {
						var instance = this;

						instance._bindSidebar();
						instance._bindXMLLog();
					},

					handleCommandCompleted: function(id) {
						var instance = this;

						var logIdSelector = '.command-log[data-logId="' + instance.get(STR_COMMAND_LOG_ID) + '"]';

						var commandLog = instance.get(STR_SIDEBAR).one(logIdSelector);

						var latestCommand = commandLog.one('.line-group:last-child');

						if (latestCommand) {
							instance._parseCommandLog(latestCommand);

							var linkedFunction = instance.get(STR_XML_LOG).one('#' + id);

							instance._displayNode(linkedFunction);

							instance._selectCurrentScope(linkedFunction);

							instance._setXmlNodeClass(linkedFunction);

							if (latestCommand.hasClass('failed')) {
								instance._injectXmlError(latestCommand);
							}
						}
					},

					handleCurrentCommandSelect: function(event) {
						var instance = this;

						var currentTargetAncestor = event.currentTarget.ancestor();

						if (currentTargetAncestor) {
							if (!currentTargetAncestor.hasClass('current-scope')) {
								event.halt(true);
							}

							instance._parseCommandLog(currentTargetAncestor);

							var functionLinkId = currentTargetAncestor.getData(ATTR_DATA_FUNCTION_LINK_ID);

							var xmlLog = instance.get(STR_XML_LOG);

							var linkedFunction = xmlLog.one('.line-group[data-functionLinkId="' + functionLinkId + '"]');

							instance._displayNode(linkedFunction);

							instance._selectCurrentScope(linkedFunction);
						}
					},

					handleCurrentScopeSelect: function(event) {
						var instance = this;

						var currentTargetAncestor = event.currentTarget.ancestor();

						if (currentTargetAncestor) {
							if (!currentTargetAncestor.hasClass('current-scope')) {
								event.halt(true);
							}

							instance._displayNode(currentTargetAncestor);

							instance._selectCurrentScope(currentTargetAncestor);
						}
					},

					handleErrorBtns: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						currentTarget.toggleClass(CSS_TOGGLE);

						var xmlLog = instance.get(STR_XML_LOG);

						var errorLinkId = currentTarget.getData(ATTR_DATA_ERROR_LINK_ID);

						var errorPanel = xmlLog.one('.errorPanel[data-errorLinkId="' + errorLinkId + '"]');

						if (errorPanel) {
							errorPanel.toggleClass(CSS_TOGGLE);
						}
					},

					handleFullScreenImageClick: function(event) {
						var instance = this;

						var fullScreenImageContainer = A.one('#fullScreenImage');

						if (fullScreenImageContainer) {
							var src;

							var fullScreenImage = fullScreenImageContainer.one('#image');

							if (fullScreenImage) {
								var currentTarget = event.currentTarget;

								src = currentTarget.attr(STR_SRC);

								if (src) {
									fullScreenImage.attr(STR_SRC, src);
								}

								fullScreenImage.toggleClass('hide', !src);
							}

							fullScreenImageContainer.toggleClass(CSS_TOGGLE, !src);
						}
					},

					handleGoToErrorBtn: function(event) {
						var instance = this;

						instance._displayNode();
					},

					handleLineTrigger: function(id, starting) {
						var instance = this;

						var linkedLine = instance.get(STR_XML_LOG).one('#' + id);

						instance._setXmlNodeClass(linkedLine);

						var container = linkedLine.one('> .child-container');

						if (container) {
							if (starting && container.hasClass(CSS_COLLAPSE)) {
								instance._toggleContainer(container, false);
								instance._scrollToNode(linkedLine);
							}

							else if (!starting && !container.hasClass(CSS_COLLAPSE)) {
								instance._toggleContainer(container, false);
							}
						}
					},

					handleMinimizeSidebarBtn: function(event) {
						var instance = this;

						instance._minimizeSidebar(event.currentTarget);
					},

					handleToggleCollapseBtn: function(event, inSidebar) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (!currentTarget.hasClass('btn')) {
							currentTarget = currentTarget.previous();

							if (!inSidebar) {
								currentTarget = currentTarget.one('.btn-collapse');
							}
						}

						if (currentTarget) {
							var lookUpScope = instance.get(STR_XML_LOG);

							if (inSidebar) {
								lookUpScope = instance.get(STR_SIDEBAR);
							}

							var linkId = currentTarget.getData(ATTR_DATA_BUTTON_LINK_ID);

							var collapsibleNode = lookUpScope.one('.child-container[data-btnLinkId="' + linkId + '"]');

							instance._toggleContainer(collapsibleNode, inSidebar);
						}
					},

					handleToggleCommandLogBtn: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var logId = currentTarget.getData(ATTR_DATA_LOG_ID);

						var commandLog = instance._getCommandLogNode(logId);

						instance._toggleCommandLog(commandLog, currentTarget);
					},

					_bindSidebar: function() {
						var instance = this;

						var sidebar = instance.get(STR_SIDEBAR);

						sidebar.delegate(
							'click',
							A.bind('handleCurrentCommandSelect', instance),
							'.linkable .line-container'
						);

						sidebar.delegate(
							'click',
							A.rbind('handleToggleCollapseBtn', instance, true),
							'.expand-toggle, .linkable.current-scope .line-container'
						);

						var logBtn = sidebar.all('.btn-command-log');

						logBtn.on(
							'click',
							A.bind('handleToggleCommandLogBtn', instance)
						);

						var sidebarBtn = sidebar.one('.btn-sidebar');

						if (sidebarBtn) {
							sidebarBtn.on(
								'click',
								A.bind('handleMinimizeSidebarBtn', instance)
							);
						}

						var jumpToError = sidebar.one('.btn-jump-to-error');

						if (jumpToError) {
							jumpToError.on(
								'click',
								A.bind('handleGoToErrorBtn', instance)
							);
						}
					},

					_bindXMLLog: function() {
						var instance = this;

						var xmlLog = instance.get(STR_XML_LOG);

						xmlLog.delegate(
							'click',
							A.bind('handleCurrentScopeSelect', instance),
							'.conditional-function > .line-container, .function > .line-container, .macro > .line-container, .test-group > .line-container'
						);

						xmlLog.delegate(
							'click',
							A.bind('handleFullScreenImageClick', instance),
							'.fullscreen-image, .screenshot-container img'
						);

						xmlLog.delegate(
							'click',
							A.rbind('handleToggleCollapseBtn', instance, false),
							'.btn-collapse, .btn-var, .current-scope > .line-container'
						);

						xmlLog.delegate(
							'click',
							A.bind('handleErrorBtns', instance),
							'.btn-error, .btn-screenshot'
						);
					},

					_clearXmlErrors: function(command) {
						command.all('.errorPanel').remove();

						var btnContainer = command.one('.btn-container');

						btnContainer.all('[data-' + ATTR_DATA_ERROR_LINK_ID + ']').remove();
					},

					_collapseTransition: function(targetNode) {
						var instance = this;

						var returnVal = false;

						var running = instance.get(STR_RUNNING);

						if (targetNode && (!running || !running.contains(targetNode))) {
							var height;

							var collapsing = targetNode.getStyle(STR_HEIGHT) != '0px';

							if (collapsing) {
								height = targetNode.outerHeight();

								targetNode.height(height);

								instance.set(STR_RUNNING, targetNode);

								targetNode.addClass(CSS_TRANSITIONING);
							}
							else {
								var lastChild = targetNode.getDOMNode().lastElementChild;

								lastChild = A.one(lastChild);

								targetNode.removeClass(CSS_COLLAPSE);
								targetNode.addClass(CSS_TRANSITIONING);

								var lastChildHeight = lastChild.innerHeight();
								var lastChildY = lastChild.getY();

								var lastChildBottomY = lastChildY + lastChildHeight + 1;

								height = lastChildBottomY - targetNode.getY();
							}

							instance._getTransition(targetNode, height, collapsing);

							returnVal = true;
						}

						return returnVal;
					},

					_displayNode: function(node) {
						var instance = this;

						node = node || instance.get(STR_FAILS).last();

						if (node) {
							var parentContainers = node.ancestors('.child-container');

							if (parentContainers) {
								instance._expandParentContainers(parentContainers, node);
							}
						}
					},

					_expandParentContainers: function(parentContainers, node) {
						var instance = this;

						var timeout = 0;

						var container = parentContainers.shift();

						if (container.hasClass(CSS_COLLAPSE)) {
							instance._toggleContainer(container, false);

							instance._scrollToNode(container.one('.line-group'));

							timeout = 200;
						}

						if (parentContainers.size()) {
							setTimeout(
								A.bind('_expandParentContainers', instance, parentContainers, node),
								timeout
							);
						}
						else {
							instance._scrollToNode(node);
						}
					},

					_getCommandLogNode: function(logId) {
						var instance = this;

						logId = logId || instance.get(STR_COMMAND_LOG_ID);

						var logIdSelector = '.command-log[data-logId="' + logId + '"]';

						return instance.get(STR_SIDEBAR).one(logIdSelector);
					},

					_getTransition: function(targetNode, height, collapsing) {
						var instance = this;

						var duration = Math.pow(height, 0.35) / 15;

						var ease = 'ease-in';

						var margin = 0;

						if (collapsing) {
							ease = 'ease-out';

							height = 0;
						}

						else {
							targetNode.addClass('in');

							margin = targetNode.getStyle('marginTop');

							targetNode.removeClass('in');
						}

						targetNode.transition(
							{
								height: {
									duration: duration,
									easing: ease,
									value: height
								},

								marginTop: {
									duration: 0.1,
									easing: ease,
									value: margin
								},

								marginBottom: {
									duration: 0.1,
									easing: ease,
									value: margin
								}
							},
							function() {
								if (collapsing) {
									targetNode.addClass(CSS_COLLAPSE);

									targetNode.removeAttribute('style');
								}
								else {
									targetNode.height('auto');
								}

								instance.set(STR_RUNNING, null);

								targetNode.removeClass(CSS_TRANSITIONING);
							}
						);
					},

					_injectXmlError: function(command) {
						var instance = this;

						var consoleLog = command.one('.console');
						var screenshots = command.one('.screenshots');

						if (screenshots) {
							screenshots.attr('class', 'screenshots-log');

							consoleLog.append(screenshots);
						}

						var functionLinkId = command.getData(ATTR_DATA_FUNCTION_LINK_ID);

						var functionLinkIdSelector = '.line-group[data-functionLinkId="' + functionLinkId + '"]';

						var failedFunction = instance.get(STR_XML_LOG).one(functionLinkIdSelector);

						if (consoleLog && failedFunction) {
							var buffer = [];

							var consoleBtn = A.Lang.sub(
								TPL_ERROR_BUTTONS,
								{
									cssClass: 'btn-error',
									linkId: consoleLog.getData(ATTR_DATA_ERROR_LINK_ID)
								}
							);

							buffer.push(consoleBtn);

							buffer = buffer.join(STR_BLANK);

							var btnContainer = failedFunction.one('.btn-container');

							btnContainer.append(buffer);

							failedFunction.append(consoleLog.clone());
						}
					},

					_minimizeSidebar: function(button) {
						var instance = this;

						instance.get(STR_CONTENT_BOX).toggleClass('minimized-sidebar');

						button = button || instance.get(STR_SIDEBAR).one('.btn-sidebar');

						button.toggleClass(CSS_TOGGLE);
					},

					_parseCommandLog: function(node) {
						var instance = this;

						var commandLogScope = instance.get(STR_COMMAND_LOG_SCOPE);

						if (commandLogScope) {
							commandLogScope.removeClass(CSS_CURRENT_SCOPE);
						}

						instance.set(
							STR_COMMAND_LOG_SCOPE,
							new A.NodeList()
						);

						if (node.hasClass('macro')) {
							var macroScope = node.all('[data-functionLinkId]');

							macroScope.each(instance._scopeCommandLog, instance);
						}
						else {
							instance._scopeCommandLog(node);
						}

						var commandLogScopeFirst = instance.get(STR_COMMAND_LOG_SCOPE).first();

						instance._scrollToNode(commandLogScopeFirst, true);
					},

					_refreshEditMenu: function() {
						var instance = this;

						var currentScope = instance.get(STR_CURRENT_SCOPE);

						if (currentScope) {
							var sidebar = instance.get(STR_SIDEBAR);

							var scopeNames = currentScope.all('> .line-container .name');
							var scopeTypes = currentScope.all('> .line-container .tag-type');

							var scopeName = scopeNames.first();

							scopeName = scopeName.html();

							var scopeType = scopeTypes.first();

							scopeType = scopeType.html();

							sidebar.one('.scope-type .scope-name').html(scopeName);
							sidebar.one('.scope-type .title').html(scopeType);

							var sidebarParameterList = sidebar.one('.parameter .parameter-list');

							sidebarParameterList.empty();

							var sidebarParameterTitle = sidebar.one('.parameter .title');

							sidebarParameterTitle.removeClass(CSS_HIDDEN);

							if (scopeType !== 'function' && scopeType !== 'macro') {
								sidebarParameterTitle.addClass(CSS_HIDDEN);
							}
							else {
								var buffer = [];

								if (scopeType === 'macro' || scopeType === 'function') {
									var increment = 2;
									var paramCollection = currentScope.all('> .line-container .child-container .name');;
									var start = 0;
									var valueIncrement = 1;


									if (scopeType === 'function') {
										increment = 1;
										paramCollection = scopeNames;
										start = 1;
										valueIncrement = 0;
									}

									var limit = paramCollection.size();

									for (var i = start; i < limit; i += increment) {
										buffer.push(
											A.Lang.sub(
												TPL_PARAMETER,
												{
													cssClass: 'parameter-name',
													parameter: scopeTypes.item(i).html()
												}
											),

											A.Lang.sub(
												TPL_PARAMETER,
												{
													cssClass: 'parameter-value',
													parameter: scopeNames.item(i + valueIncrement).html()
												}
											)
										);
									}
								}

								buffer = buffer.join(STR_BLANK);

								sidebarParameterList.append(buffer);
							}
						}
					},

					_scopeCommandLog: function(node) {
						var instance = this;

						if (node) {
							var functionLinkId = node.getData(ATTR_DATA_FUNCTION_LINK_ID);

							var functionLinkIdSelector = '.linkable[data-functionLinkId="' + functionLinkId + '"]';

							var nodes = instance.get(STR_SIDEBAR).all(functionLinkIdSelector);

							var buffer = nodes.getDOMNodes().reverse();

							var commandLogScope = instance.get(STR_COMMAND_LOG_SCOPE);

							commandLogScope = commandLogScope.concat(buffer);

							instance.set(STR_COMMAND_LOG_SCOPE, commandLogScope);

							commandLogScope.addClass(CSS_CURRENT_SCOPE);
						}
					},

					_scrollToNode: function(node, inSidebar) {
						var instance = this;

						var scrollNode = WIN;

						if (node) {
							var lineContainer = node.one('> .line-container');

							if (lineContainer) {
								var halfNodeHeight = lineContainer.innerHeight() / 2;
								var halfWindowHeight = WIN.height() / 2;

								var offsetHeight = halfWindowHeight - halfNodeHeight;

								var nodeY = lineContainer.getY();

								if (inSidebar) {
									scrollNode = instance._getCommandLogNode();

									var dividerLine = scrollNode.one('.divider-line');

									if (dividerLine) {
										nodeY -= dividerLine.getY();
									}
								}

								var yDistance = nodeY - offsetHeight;

								new A.Anim(
									{
										duration: 2,
										easing: 'easeOutStrong',
										node: scrollNode,
										to: {
											scroll: [0, yDistance]
										}
									}
								).run();
							}
						}
					},

					_selectCurrentScope: function(node) {
						var instance = this;

						var currentScope = instance.get(STR_CURRENT_SCOPE);

						if (currentScope) {
							currentScope.removeClass(CSS_CURRENT_SCOPE);
						}

						node.addClass(CSS_CURRENT_SCOPE);

						instance.set(STR_CURRENT_SCOPE, node);

						if (instance.get(STR_COMMAND_LOG_ID)) {
							instance._parseCommandLog(node);
						}

						instance._refreshEditMenu();
					},

					_setXmlNodeClass: function(node) {
						var instance = this;

						var status = instance.get(STR_STATUS);

						var statusLength = status.length;

						for (var i = 0; i < statusLength; i++) {
							node.removeClass(status[i]);
						}

						var selector = 'status' + instance.get(STR_COMMAND_LOG_ID);

						var currentStatus = node.getData(selector);

						node.addClass(currentStatus);
					},

					_toggleCommandLog: function(commandLog, button) {
						var instance = this;

						var commandLogId = instance.get(STR_COMMAND_LOG_ID);
						var sidebar = instance.get(STR_SIDEBAR);

						var logId = commandLog.getData(ATTR_DATA_LOG_ID);

						var logIdSelector = '.btn-command-log[data-logId="' + logId + '"]';

						button = button || sidebar.one(logIdSelector);

						button.toggleClass(CSS_TOGGLE);

						var newLogId;

						if (commandLogId !== logId) {
							if (commandLogId) {
								var currentActiveLog = instance._getCommandLogNode();

								instance._toggleCommandLog(currentActiveLog);
							}

							newLogId = logId;

							var commandFailures = commandLog.all('.failed');

							commandFailures.each(instance._injectXmlError, instance);
						}
						else {
							newLogId = null;

							var fails = instance.get(STR_XML_LOG).all(SELECTOR_FAIL);

							if (fails.size()) {
								fails.each(instance._clearXmlErrors);
							}
						}

						instance.set(STR_COMMAND_LOG_ID, newLogId);

						instance._toggleXmlLogClasses(logId);

						var failNodes = instance.get(STR_XML_LOG).all(SELECTOR_FAIL);

						instance.set(STR_FAILS, failNodes);

						instance._transitionCommandLog(commandLog);

						if (failNodes.size()) {
							failNodes.each(instance._displayNode, instance);

							instance._selectCurrentScope(failNodes.first());
						}
					},

					_toggleContainer: function(collapsibleContainer, inSidebar) {
						var instance = this;

						var lookUpScope = instance.get(STR_XML_LOG);

						if (inSidebar) {
							lookUpScope = instance.get(STR_SIDEBAR);
						}

						var linkId = collapsibleContainer.getData(ATTR_DATA_BUTTON_LINK_ID);

						var collapsibleBtn = lookUpScope.one('.btn[data-btnLinkId="' + linkId + '"]');

						var collapsed = instance._collapseTransition(collapsibleContainer);

						if (collapsed && collapsibleBtn) {
							collapsibleBtn.toggleClass(CSS_TOGGLE);
						}
					},

					_toggleXmlLogClasses: function(logId) {
						var instance = this;

						var status = instance.get(STR_STATUS);

						var selector = 'data-status' + logId;

						var statusLength = status.length;

						for (var i = 0; i < statusLength; i++) {
							var currentStatus = status[i];

							var currentStatusSelector = '[' + selector + '="' + currentStatus + '"]';

							var currentStatusNodes = A.all(currentStatusSelector);

							currentStatusNodes.toggleClass(currentStatus);
						}
					},

					_transitionCommandLog: function(commandLog) {
						var instance = this;

						commandLog.toggleClass(CSS_COLLAPSE);

						instance.get(STR_CONTENT_BOX).toggleClass('command-logger');

						var lastChildLog = commandLog.one('.line-group:last-child');

						if (lastChildLog && lastChildLog.hasClass(CSS_COLLAPSE)) {
							instance._toggleContainer(lastChildLog, true);
						}

						var commandLogId = instance.get(STR_COMMAND_LOG_ID);
						var commandLogScope = instance.get(STR_COMMAND_LOG_SCOPE);

						if (commandLogId && commandLogScope) {
							instance._scrollToNode(commandLogScope.first(), true);
						}
					}
				}
			}
		);

		A.PoshiLogger = PoshiLogger;
	},
	'',
	{
		requires: ['anim', 'aui-base', 'aui-component', 'aui-node', 'event', 'resize', 'transition', 'widget']
	}
);