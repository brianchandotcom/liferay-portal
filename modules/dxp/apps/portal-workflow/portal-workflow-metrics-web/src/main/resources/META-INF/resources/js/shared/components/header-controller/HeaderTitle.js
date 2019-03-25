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
		const titleChanged = title !== this.prevTitle;

		return (
			<PortalComponent container={container} replace>
				{titleChanged && (this.setDocumentTitle(), title)}
			</PortalComponent>
		);
	}
}