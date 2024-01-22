import ClayIcon from '@clayui/icon';
import React from 'react';
import {DEFAULT_DATE_FORMAT, formatUTCDate} from 'shared/util/date';
import {getPropIcon, getPropLabel} from 'shared/util/subscriptions';
import {PropTypes} from 'prop-types';

class PlanBreakdown extends React.Component {
	static defaultProps = {
		currentPlan: false,
		limits: []
	};

	static propTypes = {
		currentPlan: PropTypes.bool,
		limits: PropTypes.array,
		workspaceBirthday: PropTypes.number
	};

	render() {
		const {currentPlan, limits, workspaceBirthday} = this.props;

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

						{currentPlan && (
							<li>
								<ClayIcon
									className='icon-root'
									symbol='calendar-usage'
								/>

								<span className='limit-amount semibold'>
									{Liferay.Language.get('workspace-birthday')}
								</span>

								<span className='text-secondary'>
									{formatUTCDate(
										workspaceBirthday,
										DEFAULT_DATE_FORMAT
									)}
								</span>
							</li>
						)}
					</ul>
				</div>
			</div>
		);
	}
}

export default PlanBreakdown;
