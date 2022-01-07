import {
	createContext,
	useCallback,
	useContext,
	useEffect,
	useState,
} from 'react';
import {useCustomEvent} from '../../../common/hooks/useCustomEvent';
import {getLiferayGroupId} from '../../../common/services/liferay/themeDisplay';
import {DEVICES} from '../../../common/utils/constants';
import {SANITIZE_EMPTY_KEYS_REGEX} from '../../../common/utils/patterns';
import TipContainerModal from '../components/tip-container-modal';
import {
	getContentTemplates,
	getRenderedContent,
	getStructuredContentFolders,
	getStructuredContents,
} from '../services/WebContent';
import {AppContext} from './AppContextProvider';

const siteGroupId = getLiferayGroupId();
const WEB_CONTENT_NAME = 'Tip';
const WebContentContext = createContext();

const getTipFolderId = async () => {
	const {
		data: {items: structuredContentFolders = [{}]},
	} = await getStructuredContentFolders(
		siteGroupId,
		`?filter=name eq '${WEB_CONTENT_NAME}'`
	);

	const [{id}] = structuredContentFolders;

	return id;
};

const getTipTemplateId = async () => {
	const {
		data: {items: contentTemplates = [{}]},
	} = await getContentTemplates(
		siteGroupId,
		`?filter=contains(name, '${WEB_CONTENT_NAME}')`
	);

	const [{id}] = contentTemplates;

	return id;
};

const WebContentProvider = ({children}) => {
	const {
		state: {dimensions},
	} = useContext(AppContext);
	const [dispatchEvent] = useCustomEvent();
	const [context, setContext] = useState();
	const [webContentModal, setWebContentModal] = useState({
		html: '',
		show: false,
	});
	const isMobileDevice = dimensions.deviceSize !== DEVICES.DESKTOP;

	const getInitialData = async () => {
		const [
			{value: tipFolderId},
			{value: tipTemplateId},
		] = await Promise.allSettled([getTipFolderId(), getTipTemplateId()]);

		if (!tipFolderId) {
			return console.warn('Raylife TIP Folder not found');
		}

		if (!tipTemplateId) {
			return console.warn('Raylife TIP Template not found');
		}

		const {data: structuredContents} = await getStructuredContents(
			tipFolderId
		);

		setContext({
			structuredContents: structuredContents.items,
			tipFolderId,
			tipTemplateId,
		});
	};

	const prepareHtmlElement = (html) => {
		html = html.replace(SANITIZE_EMPTY_KEYS_REGEX, '');

		const container = document.createElement('div');

		container.innerHTML = html;

		container.querySelector('#dismiss').remove();

		setWebContentModal({html: container.innerHTML, show: true});
	};

	const dispatchCustomEvent = useCallback(
		async (data, event) => {
			if (isMobileDevice) {
				const structuredContent = context.structuredContents.find(
					({friendlyUrlPath}) => friendlyUrlPath === data.templateName
				);

				if (structuredContent?.id) {
					const {data: html} = await getRenderedContent(
						structuredContent.id,
						context.tipTemplateId
					);

					return prepareHtmlElement(html);
				}
			}

			dispatchEvent(data, event);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[isMobileDevice, context]
	);

	useEffect(() => {
		if (isMobileDevice && !context) {
			getInitialData();
		}
	}, [isMobileDevice, context]);

	return (
		<WebContentContext.Provider value={[context, dispatchCustomEvent]}>
			<TipContainerModal
				setWebContentModal={setWebContentModal}
				webContentModal={webContentModal}
			/>

			{children}
		</WebContentContext.Provider>
	);
};

export default WebContentProvider;

export {WebContentContext};
