import PortalComponent from './PortalComponent';
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