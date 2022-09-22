package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBMailingList;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.view.count.listener.ViewCountEntryModelListener;
import com.liferay.view.count.model.ViewCountEntry;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
	immediate = true,
	service = ViewCountEntryModelListener.class
)
public class MBThreadViewCountEntryModelListener implements
	ViewCountEntryModelListener {

	@Override
	public void afterIncrement(ViewCountEntry viewCountEntry) {

		 _mbMessageLocalService.fetchMBMessage(_mbThreadLocalService.fetchMBThread(viewCountEntry.getClassPK()).getRootMessageId());

		Indexer<MBMessage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		try {
			indexer.reindex(_mbMessageLocalService.fetchMBMessage(_mbThreadLocalService.fetchMBThread(viewCountEntry.getClassPK()).getRootMessageId()));
		}
		catch (SearchException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String getModelClassName(){
		return MBThread.class.getName();
	}

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;
}