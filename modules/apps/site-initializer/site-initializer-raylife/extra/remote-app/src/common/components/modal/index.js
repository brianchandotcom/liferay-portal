import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect} from 'react';
import useImperativeDisableScroll from '../../hooks/useImperativeDisableScroll';

const MODAL_BACKGROUND_ID = 'modal-backdrop';

const Modal = ({
	children,
	footer,
	onClose = () => {},
	modalSize = 'md',
	show,
}) => {
	useImperativeDisableScroll({disabled: show, element: document.body});

	useEffect(() => {
		const onClickElementListener = (event) => {
			const [firstPath] = event.path ||
				(event.composedPath && event.composedPath()) || [{}];

			if (firstPath.id === MODAL_BACKGROUND_ID) {
				onClose();
			}
		};

		if (show) {
			document.addEventListener('mousedown', onClickElementListener);
			document.addEventListener('touchstart', onClickElementListener);
		}

		return () => {
			document.removeEventListener('mousedown', onClickElementListener);
			document.removeEventListener('touchstart', onClickElementListener);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [show]);

	return (
		<div
			className={classNames(
				'h-100 w-100 overflow-auto position-fixed backdrop',
				{
					'd-block': show,
					'd-none': !show,
				}
			)}
			id={MODAL_BACKGROUND_ID}
		>
			<div
				className={`bg-neutral-0 rounded m-auto p-3 position-absolute modal-content modal-${modalSize} px-3 py-4`}
			>
				<div className="align-items-center border-bottom-0 d-flex justify-content-flex-end modal-header p-0">
					<div className="close" onClick={onClose}>
						<ClayIcon symbol="times" />
					</div>
				</div>

				{children}

				{footer}
			</div>
		</div>
	);
};

export default Modal;
