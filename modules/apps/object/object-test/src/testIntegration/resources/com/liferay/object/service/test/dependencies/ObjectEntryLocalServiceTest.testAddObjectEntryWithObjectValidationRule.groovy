import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

Calendar startCalendar = new GregorianCalendar();

startCalendar.setTime(GetterUtil.getDate(birthday, DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd", LocaleUtil.US)));
startCalendar.add(Calendar.MILLISECOND, 0);

Calendar endCalendar = new GregorianCalendar();

endCalendar.setTime(new Date());
endCalendar.add(Calendar.MILLISECOND, 0);

int yearsBetween = 0;

while (CalendarUtil.beforeByDay(startCalendar.getTime(), endCalendar.getTime())) {
	startCalendar.add(Calendar.YEAR, 1);

	yearsBetween++;
}

returnValue = (yearsBetween >= 18L) ? true : false;