import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
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
					<ClayButton
						className='button-root'
						displayType='secondary'
						key='previousStep'
						onClick={onPreviousStep}
					>
						{Liferay.Language.get('previous')}
					</ClayButton>
				)}

				<ClayLink
					button
					className='button-root cancel'
					displayType='secondary'
					href={cancelHref}
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>

				<ClayButton
					className='button-root'
					disabled={!enableNext}
					displayType='primary'
					onClick={onNextStep}
					type={onNextStep ? 'button' : 'submit'}
				>
					{submitting && (
						<ClayLoadingIndicator
							className='d-inline-block mr-2'
							displayType='secondary'
							size='sm'
						/>
					)}

					{submitMessage}
				</ClayButton>
			</div>
		);
	}
}

export default FormNavigation;
