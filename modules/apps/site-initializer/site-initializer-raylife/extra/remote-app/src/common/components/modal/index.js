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
			className={classNames('backdrop', {
				show,
			})}
			id={MODAL_BACKGROUND_ID}
		>
			<div className={`modal-content modal-${modalSize}`}>
				<div className="modal-header">
					<div className="close" onClick={onClose}>
						<ClayIcon symbol="times-small" />
					</div>
				</div>

				{children}

				{footer}
			</div>
		</div>
	);
};

export default Modal;
