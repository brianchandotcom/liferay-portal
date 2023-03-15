import Label from 'shared/components/Label';
import React from 'react';
import {PropTypes} from 'prop-types';
import {sub} from 'shared/util/lang';

const AnnualPrice = ({price}) => (
	<div className='annual-price'>
		<div>
			<strong>
				{sub(Liferay.Language.get('x-usd'), [price.toLocaleString()])}
			</strong>

			<span className='text-secondary small'>{` / ${Liferay.Language.get(
				'year'
			).toLowerCase()}`}</span>
		</div>
	</div>
);

class SubscriptionTitle extends React.Component {
	static propTypes = {
		labelText: PropTypes.string,
		name: PropTypes.node,
		price: PropTypes.number
	};

	render() {
		const {labelText, name, price} = this.props;

		return (
			<div
				className={`plan-title${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<div className='plan-name'>{name}</div>

				{!!labelText && (
					<Label display='primary' size='lg' uppercase>
						{labelText}
					</Label>
				)}

				{price > -1 && <AnnualPrice price={price} />}
			</div>
		);
	}
}

export default SubscriptionTitle;
