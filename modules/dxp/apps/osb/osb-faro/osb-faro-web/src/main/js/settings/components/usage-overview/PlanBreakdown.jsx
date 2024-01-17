import ClayIcon from '@clayui/icon';
import React from 'react';
import {getPropIcon, getPropLabel} from 'shared/util/subscriptions';
import {PropTypes} from 'prop-types';

class PlanBreakdown extends React.Component {
	static defaultProps = {
		addOns: [],
		limits: []
	};

	static propTypes = {
		addOns: PropTypes.array,
		limits: PropTypes.array
	};

	render() {
		const {limits} = this.props;

		return (
			<div
				className={`plan-breakdown small${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<div className='limits'>
					<ul>
						{limits.map(limit => (
							<li key={limit.entityLabel}>
								<ClayIcon
									className='icon-root'
									symbol={getPropIcon(limit.entityLabel)}
								/>

								<span className='limit-amount semibold'>
									{limit.value.toLocaleString()}
								</span>

								<span className='text-secondary'>
									{getPropLabel(limit.entityLabel)}
								</span>
							</li>
						))}

						<li>
							<ClayIcon className='icon-root' symbol='ac-users' />

							<span className='limit-amount semibold'>
								{Liferay.Language.get('unlimited')}
							</span>

							<span className='text-secondary'>
								{Liferay.Language.get('users')}
							</span>
						</li>
					</ul>
				</div>
			</div>
		);
	}
}

export default PlanBreakdown;
