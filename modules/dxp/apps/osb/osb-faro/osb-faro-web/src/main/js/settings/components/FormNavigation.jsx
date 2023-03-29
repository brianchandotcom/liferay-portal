import Button from 'shared/components/Button';
import getCN from 'classnames';
import React from 'react';
import {PropTypes} from 'prop-types';

class FormNavigation extends React.Component {
	static defaultProps = {
		enableNext: false,
		submitMessage: Liferay.Language.get('next')
	};

	static propTypes = {
		cancelHref: PropTypes.string,
		className: PropTypes.string,
		enableNext: PropTypes.bool,
		onNextStep: PropTypes.func,
		onPreviousStep: PropTypes.func,
		submitMessage: PropTypes.string,
		submitting: PropTypes.bool
	};

	render() {
		const {
			cancelHref,
			className,
			enableNext,
			onNextStep,
			onPreviousStep,
			submitMessage,
			submitting
		} = this.props;

		return (
			<div className={getCN('form-navigation-root', className)}>
				{onPreviousStep && (
					<Button
						display='secondary'
						key='previousStep'
						onClick={onPreviousStep}
					>
						{Liferay.Language.get('previous')}
					</Button>
				)}

				<Button className='cancel' href={cancelHref}>
					{Liferay.Language.get('cancel')}
				</Button>

				<Button
					disabled={!enableNext}
					display='primary'
					loading={submitting}
					onClick={onNextStep}
					type={onNextStep ? 'button' : 'submit'}
				>
					{submitMessage}
				</Button>
			</div>
		);
	}
}

export default FormNavigation;
