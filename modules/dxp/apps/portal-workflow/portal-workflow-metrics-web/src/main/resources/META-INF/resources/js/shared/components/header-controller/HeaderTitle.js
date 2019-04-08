import PortalComponent from './PortalComponent';
import React from 'react';

export default class HeaderTitle extends React.Component {
	componentDidUpdate(prevProps) {
		this.prevTitle = prevProps.title;
	}

	setDocumentTitle() {
		document.title = document.title.replace(this.prevTitle, this.props.title);
	}

	render() {
		const { container, title } = this.props;

		if (title != this.prevTitle) {
			this.setDocumentTitle();
		}

		return (
			<PortalComponent container={container} replace>
				{title}
			</PortalComponent>
		);
	}
}