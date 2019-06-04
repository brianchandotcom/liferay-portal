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
			}
			else {
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

		return (
			<React.Fragment>
				{ReactDOM.createPortal(children, this.element)}
			</React.Fragment>
		);
	}
}