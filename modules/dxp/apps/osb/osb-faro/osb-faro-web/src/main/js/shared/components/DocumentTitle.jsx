import React from 'react';
import {hasChanges} from 'shared/util/react';
import {PropTypes} from 'prop-types';
import {BrowserRouter as Router} from 'react-router-dom';

class DocumentTitle extends React.Component {
	static propTypes = {
		title: PropTypes.string
	};

	constructor(props) {
		super(props);

		this._router = Router;
	}

	componentDidMount() {
		this.setTitle();
	}

	componentDidUpdate(prevProps) {
		if (hasChanges(prevProps, this.props, 'title')) {
			this.setTitle();
		}
	}

	setTitle() {
		const {title} = this.props;

		const defaultTitle = Liferay.Language.get('analytics-cloud');

		const newTitle = title ? `${title} - ${defaultTitle}` : defaultTitle;

		document.title = newTitle;
	}

	render() {
		return null;
	}
}

export default DocumentTitle;
