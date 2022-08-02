import com.liferay.portal.kernel.util.CalendarUtil
import com.liferay.portal.kernel.util.DateFormatFactoryUtil
import com.liferay.portal.kernel.util.GetterUtil
import com.liferay.portal.kernel.util.LocaleUtil

int yearsBetween = 0;

Calendar startCalendar = new GregorianCalendar();

startCalendar.setTime(GetterUtil.getDate(birthday, DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd", LocaleUtil.US)));

Calendar endCalendar = new GregorianCalendar();

endCalendar.setTime(new Date());

while (CalendarUtil.beforeByDay(startCalendar.getTime(), endCalendar.getTime())) {
	startCalendar.add(Calendar.YEAR, 1);

	yearsBetween++;
}

invalidFields = (yearsBetween < 18L);