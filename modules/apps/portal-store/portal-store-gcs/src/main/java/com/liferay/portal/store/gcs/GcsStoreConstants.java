package com.liferay.portal.store.gcs;

import com.liferay.document.library.kernel.util.comparator.VersionNumberComparator;

public interface GcsStoreConstants {

  String KEY_PROPERTY = "dl.store.gcs.aes256.key";

  VersionNumberComparator VERSION_NUMBER_COMPARATOR =
      new VersionNumberComparator();

}
