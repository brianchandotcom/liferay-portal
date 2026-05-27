import ClayButton from '@clayui/button';
import getCN from 'classnames';
import React from 'react';
import {Conjunctions, SUPPORTED_CONJUNCTION_OPTIONS} from '../utils/constants';

interface IConjunctionProps extends React.HTMLAttributes<HTMLButtonElement> {
	conjunctionName: string;
	disabled?: boolean;
	sequential?: boolean;
}

class Conjunction extends React.Component<IConjunctionProps> {
	getConjunctionLabel(conjunctionName: string, sequential?: boolean) {
		if (sequential && conjunctionName === Conjunctions.And) {
			return Liferay.Language.get('then');
		}

		const conjunction = SUPPORTED_CONJUNCTION_OPTIONS.find(
			({name}) => name === conjunctionName
		);

		return conjunction ? conjunction.label : undefined;
	}

	render() {
		const {className, conjunctionName, disabled, onClick, sequential} =
			this.props;

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
					disabled={disabled}
					displayType='secondary'
					onClick={onClick}
					size='sm'
				>
					{this.getConjunctionLabel(conjunctionName, sequential)}
				</ClayButton>

				<div className='separator' />
			</div>
		);
	}
}

export default Conjunction;
