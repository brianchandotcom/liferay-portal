/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import ReactDOM from 'react-dom';

export default class PortalComponent extends React.Component {
	constructor(props) {
		super(props);
		this.element = document.createElement('div');
	}

	componentDidMount() {
		const {container, replace} = this.props;

		if (!container) {
			return;
		}

		if (replace) {
			if (container.children.length) {
				container.removeChild(container.children[0]);
			} else {
				container.innerHTML = '';
			}
		}

		container.appendChild(this.element);
	}

	render() {
		const {children, container} = this.props;

		if (!container) {
			return null;
		}

		return <>{ReactDOM.createPortal(children, this.element)}</>;
	}
}
