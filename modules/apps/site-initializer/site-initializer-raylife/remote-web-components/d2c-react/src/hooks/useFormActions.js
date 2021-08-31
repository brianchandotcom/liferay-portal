import {useState, useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {LiferayService} from '../services/liferay';
import {useStepWizard} from './useStepWizard';
import Cookies from 'js-cookie';
import {verifyInputAgentPage} from '../utils/contact-agent';
import {useTriggerContext} from './useTriggerContext';
/**
 *
 * @param {String} form <useWatch>
 * @param {String?} previousSection
 * @param {String?} nextSection
 * @param {String?} errorMessage
 * @returns
 */

const useFormActions = (form, previousSection, nextSection, errorMessage) => {
	const [applicationId, setApplicationId] = useState();
	const {setError, setValue} = useFormContext();
	const {setSection} = useStepWizard();
	const {updateState} = useTriggerContext();

	/**
	 * @description When the application is created, we set the value to Form Context
	 * We tried to use setValue directly on goToPrevious and goToNextForm
	 * and for reasons unknowns, the section is not called.
	 */

	useEffect(() => {
		if (applicationId) {
			setValue('basics.applicationId', applicationId);

			Cookies.set('raylife-application-id', applicationId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [applicationId]);

	useEffect(() => {
		Cookies.set('raylife-application-form', JSON.stringify(form));
	}, [form]);

	const _smoothScroll = () => {
		window.scroll({
			behavior: 'smooth',
			left: 0,
			top: 0,
		});
	};

	const _onValidation = () => {
		const phraseAgentPage = verifyInputAgentPage(form, nextSection);
		let validated = true;

		if (phraseAgentPage) {
			Cookies.set('raylife-contextual-message', phraseAgentPage);
			window.location.href = '/web/raylife/get-in-touch';
			validated = false;
		} else {
			Cookies.remove('raylife-contextual-message');
		}

		return validated;
	};

	const _SaveData = async () => {
		setError('continueButton', {});
		try {
			const response = await LiferayService.createOrUpdateRaylifeApplication(
				form
			);

			setApplicationId(response.data.id);

			return response;
		} catch (error) {
			setError('continueButton', {
				message:
					errorMessage ||
					'There was an error processing your request. Please try again.',
				type: 'manual',
			});
			throw error;
		}
	};

	const onPrevious = async () => {
		await _SaveData();

		if (previousSection) {
			setSection(previousSection);
		}

		updateState('');

		_smoothScroll();
	};

	const onSave = async () => {
		await _SaveData();

		window.location.href = '/web/raylife';
	};

	/**
	 * @state disabled for now
	 * @param {*} data
	 */
	const onNext = async () => {
		await _SaveData();

		const validated = _onValidation();

		if (validated) {
			if (nextSection) {
				_smoothScroll();

				return setSection(nextSection);
			}

			window.location.href = '/web/raylife/hang-tight';
		}
	};

	return {onNext, onPrevious, onSave};
};

export default useFormActions;
