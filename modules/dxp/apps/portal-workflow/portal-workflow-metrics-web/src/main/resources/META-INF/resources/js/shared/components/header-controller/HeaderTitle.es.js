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

import PortalComponent from './PortalComponent.es';
import React from 'react';

export default class HeaderTitle extends React.Component {
	componentDidUpdate({title: prevTitle}) {
		const {title} = this.props;

		if (prevTitle != title) {
			this.setDocumentTitle(prevTitle, title);
		}
	}

	setDocumentTitle(prevTitle, title) {
		document.title = document.title.replace(prevTitle, title);
	}

	render() {
		const {container, title} = this.props;

		return (
			<PortalComponent container={container} replace>
				{title}
			</PortalComponent>
		);
	}
}
