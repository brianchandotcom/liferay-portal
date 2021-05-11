<%--
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
--%>

<%@ include file="/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %>

<div class="row">
	<div class="col">
		<react:component
			module="js/App"
		/>
	</div>

	<div class="col">
		<button id="test-button-jsp">Increment jsp</button>

		<h3>JSP Counter: <span id="test-counter-jsp">0</span></h3>

		<h3>Name: <span id="test-name">Initial Name</span></h3>

		<button id="test-button-react">Increment react</button>

		<aui:script require="@liferay/frontend-js-state-web@1.0.3/index as StateModule, @liferay/frontend-js-sample-web@1.0.0/js/sharedState as SharedState">
			const buttonElementJSP = document.getElementById('test-button-jsp');
			const buttonElementReact = document.getElementById('test-button-react');
			const counterElement = document.getElementById('test-counter-jsp');
			const nameElement = document.getElementById('test-name');

			const State = StateModule.State;

			const counterAtom = State.atom('test-counter-jsp', 0);

			State.subscribe(counterAtom, (newVal) => {
				counterElement.innerText = newVal;
			});

			State.subscribe(SharedState.userAtom, (event) => {
				nameElement.innerText = event.name;
			});

			if (buttonElementJSP) {
				buttonElementJSP.addEventListener('click', () => {
					State.write(counterAtom, State.read(counterAtom) + 1);
				});
			}

			if (buttonElementReact) {
				buttonElementReact.addEventListener('click', () => {
					State.write(
						SharedState.counterAtomReact,
						State.read(SharedState.counterAtomReact) + 1
					);
				});
			}
		</aui:script>
	</div>
</div>