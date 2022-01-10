import {useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {clearExitAlert} from '../../../common/utils/exitAlert';
import {getLiferaySiteName} from '../../../common/utils/liferay';
import {smoothScroll} from '../../../common/utils/scroll';
import {useStepWizard} from '../hooks/useStepWizard';
import {createOrUpdateRaylifeApplication} from '../services/RaylifeApplication';
import {verifyInputAgentPage} from '../utils/contact-agent';

const liferaySiteName = getLiferaySiteName();

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

	/**
	 * @description When the application is created, we set the value to Form Context
	 * We tried to use setValue directly on goToPrevious and goToNextForm
	 * and for reasons unknowns, the section is not called.
	 */

	useEffect(() => {
		if (applicationId) {
			setValue('basics.applicationId', applicationId);

			Storage.setItem(STORAGE_KEYS.APPLICATION_ID, applicationId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [applicationId]);

	useEffect(() => {
		Storage.setItem(STORAGE_KEYS.APPLICATION_FORM, JSON.stringify(form));
	}, [form]);

	const _onValidation = () => {
		const phraseAgentPage = verifyInputAgentPage(form, nextSection);
		let validated = true;

		if (phraseAgentPage) {
			Storage.setItem(STORAGE_KEYS.CONTEXTUAL_MESSAGE, phraseAgentPage);
			window.location.href = `${liferaySiteName}/get-in-touch`;
			validated = false;
		}
		else {
			Storage.removeItem(STORAGE_KEYS.CONTEXTUAL_MESSAGE);
		}

		return validated;
	};

	const onSave = async () => {
		setError('continueButton', {});

		try {
			const response = await createOrUpdateRaylifeApplication(form);

			setApplicationId(response.data.id);

			return response;
		}
		catch (error) {
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
		await onSave();

		if (previousSection) {
			setSection(previousSection);
		}

		smoothScroll();
	};

	/**
	 * @state disabled for now
	 * @param {*} data
	 */
	const onNext = async () => {
		await onSave();

		clearExitAlert();

		const validated = _onValidation();

		if (validated) {
			if (nextSection) {
				setSection(nextSection);

				return smoothScroll();
			}

			window.location.href = `${liferaySiteName}/hang-tight`;
		}
	};

	return {onNext, onPrevious, onSave};
};

export default useFormActions;
