import {Link, withRouter} from 'react-router-dom';
import pathToRegexp from 'path-to-regexp';
import React from 'react';
import {sub} from '../../shared/util/lang';

/**
 * Results bar component.
 * @class
 * @memberof process-list
 */
class ResultsBar extends React.Component {
	render() {
		const {
			location: {search},
			match: {
				params: {page, pageSize, search: term, sort},
				path
			},
			totalCount
		} = this.props;

		const pathname = pathToRegexp.compile(path)({page, pageSize, sort});

		let resultText = Liferay.Language.get('x-results-for-x');

		if (totalCount === 1) {
			resultText = Liferay.Language.get('x-result-for-x');
		}

		return (
			<nav className='tbar tbar-inline-xs-down subnav-tbar subnav-tbar-primary'>
				<div className='container-fluid container-fluid-max-xl'>
					<ul className='tbar-nav tbar-nav-wrap'>
						<li className='tbar-item tbar-item-expand'>
							<div className='tbar-section'>
								<span className='component-text text-truncate-inline'>
									<span className='text-truncate'>
										{sub(resultText, [totalCount, term])}
									</span>
								</span>
							</div>
						</li>

						<li className='tbar-item'>
							<div className='tbar-section'>
								<Link
									className='component-link tbar-link'
									to={{
										pathname,
										search
									}}
								>
									<span>
										{Liferay.Language.get('clear-all')}
									</span>
								</Link>
							</div>
						</li>
					</ul>
				</div>
			</nav>
		);
	}
}

export default withRouter(ResultsBar);
export {ResultsBar};
