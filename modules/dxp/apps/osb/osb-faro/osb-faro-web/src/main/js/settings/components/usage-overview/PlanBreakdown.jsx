import ClayIcon from '@clayui/icon';
import React from 'react';
import {
	getPropIcon,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS
} from 'shared/util/subscriptions';
import {PropTypes} from 'prop-types';
import {sub} from 'shared/util/lang';

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
		const {addOns, limits} = this.props;

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

				{!!addOns.length && (
					<div className='addons'>
						<p className='text-secondary semibold addon-list-header'>
							{Liferay.Language.get('add-ons')}
						</p>

						<ul>
							{addOns.map(({limits, name, price}) => {
								const {entityLabel, value} = [
									{
										entityLabel: INDIVIDUALS,
										value: limits[INDIVIDUALS]
									},
									{
										entityLabel: PAGEVIEWS,
										value: limits[PAGEVIEWS]
									}
								].find(limit => limit.value);

								return (
									<li
										className={`extra-small${
											this.props.className
												? ` ${this.props.className}`
												: ''
										}`}
										key={name}
									>
										<span className='limit-amount semibold'>
											{value.toLocaleString()}
										</span>

										<span className='text-secondary'>
											{sub(
												Liferay.Language.get(
													'x-for-x-usd'
												),
												[
													<b
														className='addon-name'
														key={entityLabel}
													>
														{getPropLabel(
															entityLabel
														)}
													</b>,
													price.toLocaleString()
												],
												false
											)}
										</span>
									</li>
								);
							})}
						</ul>
					</div>
				)}
			</div>
		);
	}
}

export default PlanBreakdown;
