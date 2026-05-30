import {close, modalTypes, open} from 'shared/actions/modals';
import {useBlocker} from 'react-router-dom';
import {useDispatch} from 'react-redux';
import {useEffect} from 'react';

/**
 * Blocks in-app navigation while there are unsaved changes and prompts the
 * user with the Clay confirmation modal. Replaces the React Router v5
 * `<Prompt>` + `getUserConfirmation` flow removed in v6.
 *
 * Requires a data router (`createBrowserRouter`), which `useBlocker` depends on.
 */
export function useUnsavedChangesPrompt(when: boolean, message?: string): void {
	const dispatch = useDispatch();

	const blocker = useBlocker(
		({currentLocation, nextLocation}) =>
			when && currentLocation.pathname !== nextLocation.pathname
	);

	useEffect(() => {
		if (blocker.state === 'blocked') {
			dispatch(
				open(modalTypes.CONFIRMATION_MODAL, {
					cancelMessage: Liferay.Language.get('stay-on-page'),
					closeAfterSubmit: false,
					message,
					modalVariant: 'modal-warning',
					onClose: () => {
						dispatch(close());

						blocker.reset?.();
					},
					onSubmit: () => {
						dispatch(close());

						blocker.proceed?.();
					},
					submitButtonDisplay: 'warning',
					submitMessage: Liferay.Language.get('leave-page'),
					title: Liferay.Language.get('unsaved-changes'),
					titleIcon: 'warning-full'
				})
			);
		}
	}, [blocker.state]);
}

export default useUnsavedChangesPrompt;
