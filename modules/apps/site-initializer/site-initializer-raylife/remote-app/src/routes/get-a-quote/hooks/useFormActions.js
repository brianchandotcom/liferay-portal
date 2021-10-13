import {useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {LiferayService} from '~/common/services/liferay';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {smoothScroll} from '~/common/utils/scroll';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';
import {verifyInputAgentPage} from '~/routes/get-a-quote/utils/contact-agent';

const liferaySiteName = LiferayService.getLiferaySiteName();

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
		} else {
			Storage.removeItem(STORAGE_KEYS.CONTEXTUAL_MESSAGE);
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

		smoothScroll();
	};

	const onSave = async () => {
		await _SaveData();

		window.location.href = liferaySiteName;
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
				setSection(nextSection);

				return smoothScroll();
			}

			window.location.href = `${liferaySiteName}/hang-tight`;
		}
	};

	return {onNext, onPrevious, onSave};
};

export default useFormActions;
