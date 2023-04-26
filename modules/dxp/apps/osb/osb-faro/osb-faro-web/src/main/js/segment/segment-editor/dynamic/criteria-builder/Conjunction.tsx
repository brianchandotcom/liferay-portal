import ClayButton from '@clayui/button';
import getCN from 'classnames';
import React from 'react';
import {SUPPORTED_CONJUNCTION_OPTIONS} from '../utils/constants';

interface IConjunctionProps extends React.HTMLAttributes<HTMLButtonElement> {
	conjunctionName: string;
}

class Conjunction extends React.Component<IConjunctionProps> {
	getConjunctionLabel(conjunctionName) {
		const conjunction = SUPPORTED_CONJUNCTION_OPTIONS.find(
			({name}) => name === conjunctionName
		);

		return conjunction ? conjunction.label : undefined;
	}

	render() {
		const {className, conjunctionName, onClick} = this.props;

		const classnames = getCN(
			'button-root',
			'conjunction-button',
			'conjunction-label',
			className
		);

		return (
			<div className='conjunction-container'>
				<ClayButton
					className={classnames}
					displayType='secondary'
					onClick={onClick}
					size='sm'
				>
					{this.getConjunctionLabel(conjunctionName)}
				</ClayButton>

				<div className='separator' />
			</div>
		);
	}
}

export default Conjunction;
