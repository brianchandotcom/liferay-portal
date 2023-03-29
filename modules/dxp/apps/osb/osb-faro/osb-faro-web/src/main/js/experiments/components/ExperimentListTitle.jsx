import autobind from 'autobind-decorator';
import BasePage from 'shared/components/base-page';
import React from 'react';
import TextTruncate from 'shared/components/TextTruncate';
import {getUrl} from 'shared/util/urls';
import {isEllipisActive} from 'shared/util/util';
import {Link} from 'react-router-dom';
import {PropTypes} from 'prop-types';
import {Routes} from 'shared/util/router';

/**
 * Touchpoint List Title
 * @class
 */
class ExperimentListTitle extends React.Component {
	static contextType = BasePage.Context;

	static propTypes = {
		id: PropTypes.string,
		title: PropTypes.string,
		touchpoint: PropTypes.string
	};

	constructor(props) {
		super(props);

		this._touchpointRef = React.createRef();
	}
	/**
	 * Handle Mouse Over
	 * @param {object} event
	 */
	@autobind
	handleMouseOver(event) {
		this.setState({
			showPopover: isEllipisActive(event)
		});
	}

	/**
	 * Handle Mouse Out
	 */
	@autobind
	handleMouseOut() {
		this.setState({
			showPopover: false
		});
	}

	/**
	 * Get Touchpoint Get url to navigate in a dashboard
	 */
	getUrl() {
		const {params, query} = this.context.router;
		const {id} = this.props;

		const router = {
			params: {
				...params,
				id
			},
			query
		};

		return getUrl(Routes.TESTS_OVERVIEW, router);
	}

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {title, touchpoint} = this.props;
		const url = this.getUrl();

		return (
			<td className='table-cell-expand'>
				<Link
					className='table-title'
					onBlur={this.handleMouseOut}
					onFocus={this.handleMouseOver}
					onMouseOut={this.handleMouseOut}
					onMouseOver={this.handleMouseOver}
					to={url}
				>
					<TextTruncate title={title || touchpoint}>
						{title || '-'}
					</TextTruncate>
				</Link>
			</td>
		);
	}
}

export default ExperimentListTitle;
