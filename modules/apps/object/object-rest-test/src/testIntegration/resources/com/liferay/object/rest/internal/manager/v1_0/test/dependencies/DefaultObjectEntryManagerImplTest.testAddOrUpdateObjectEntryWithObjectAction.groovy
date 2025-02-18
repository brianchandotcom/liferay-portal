import com.liferay.object.service.ObjectEntryLocalServiceUtil
import com.liferay.portal.kernel.service.ServiceContext

HashMap<String, Serializable> values = new HashMap<>();

values.put("integerObjectField", integerObjectField + 1);

ObjectEntryLocalServiceUtil.updateObjectEntry(creator, id, values, new ServiceContext());