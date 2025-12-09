import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import Clipboard from 'clipboard';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';
import {Alert} from 'shared/types';

const ConnectLiferayDXPTokenFragment = ({addAlert, disabled, token}) => {
	const [isUrlCopied, setIsUrlCopied] = useState(false);
	const [copyTitle, setCopyTitle] = useState(
		Liferay.Language.get('click-to-copy')
	);

	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		_clipboard.on('success', event => {
			setCopyTitle(Liferay.Language.get('copied'));

			addAlert({
				alertType: Alert.Types.Success,
				message: Liferay.Language.get(
					'copied-successfully-to-the-clipboard'
				)
			});

			setTimeout(() => {
				setCopyTitle(Liferay.Language.get('click-to-copy'));
				setIsUrlCopied(false);
			}, 3000);

			event.clearSelection();
		});

		return () => _clipboard.destroy();
	}, []);

	return (
		<ClayForm.Group
			className={getCN({
				'has-success': isUrlCopied
			})}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						disabled={disabled}
						id='token'
						insetAfter
						name='token'
						readOnly={!isUrlCopied}
						type='text'
						value={token}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						aria-label={copyTitle}
						data-clipboard-text={token}
						disabled={disabled}
						displayType={isUrlCopied ? 'success' : 'secondary'}
						onClick={() => setIsUrlCopied(true)}
						outline
						title={copyTitle}
					>
						<ClayIcon symbol={isUrlCopied ? 'check' : 'copy'} />
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

export {ConnectLiferayDXPTokenFragment};
