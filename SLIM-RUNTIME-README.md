## Build

Execute the following top level ant operation:

```
ant all -Dbuild.profile=slim
```

*Note* that the slim runtime only supports tomcat 8+. This is a limitation to simplify packaging and configuration.

## Launch

Run the tomcat start scripts as usual:

```
cd <tomcat>/bin
./startup.sh
```

## Deploy modules

Use any of the default defined module directories. Or configure a custom deploy directory (by overriding these properties):

```
    module.framework.base.dir=${liferay.home}/osgi

    module.framework.configs.dir=${module.framework.base.dir}/configs
    module.framework.marketplace.dir=${module.framework.base.dir}/marketplace
    module.framework.modules.dir=${module.framework.base.dir}/modules
    module.framework.war.dir=${module.framework.base.dir}/war

    module.framework.auto.deploy.dirs=\
        ${module.framework.configs.dir},\
        ${module.framework.marketplace.dir},\
        ${module.framework.modules.dir},\
        ${module.framework.war.dir}
```

## Running Pristine Slim Runtime

When running a slim runtime without deploying additional functionality any request will result in a 404 since there are no apps and no UI is deployed by default.

All functionality is provided by developer's modules.

## Adding functionality

The simplest type of function you can deploy is a web endpoint.

The following snippet demonstrates a simple servlet which response to all request to `http://localhost:8080[/*]`

```
package web.sample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(
	immediate = true,
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/*"
	},
	service = Servlet.class
)
public class SampleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter writer = response.getWriter();

		writer.println("<h2>Hello You!</h2>");
	}

}
```

## The DB

When the slim runtime is started for the first time, the database schema will be auto created.

The DB should look something like this:

```
MariaDB [lportal]> show tables;
+------------------+
| Tables_in_lportal|
+------------------+
| ClassName_       |
| Configuration_   |
| Counter          |
| Release_         |
| ServiceComponent |
+------------------+
5 rows in set (0.00 sec)
```

Note that this means only the following core services are available:

ClassNameLocalService
CounterLocalService
ReleaseLocalService
ServiceComponentLocalService

No other services are provided! Therefore if an existing Service Builder service *foo* is deployed, which has dependencies upon _other_ *Liferay Portal* services, since those services are not provided by the slim runtime, the service *foo* will not function.

## Service Builder

The Service Builder (runtime) will bootstrap any deployed Service Builder services (api and service modules).

An example is `com.liferay.contacts.api` & `com.liferay.contacts.service`. Similarly to the DB schema creation the Service Builder runtime will generate the required tables:

```
MariaDB [lportal]> show tables;
+------------------+
| Tables_in_lportal|
+------------------+
| ClassName_       |
| Configuration_   |
| Contacts_Entry   |
| Counter          |
| Release_         |
| ServiceComponent |
+------------------+
6 rows in set (0.00 sec)
```

## A Basic SB Web App

The following snippet shows a servlet implementing a simple web app using the contacts service.

Note how it uses OSGi Declarative Services to define it's dependencies on the `counterLocalService` provided by the Liferay core, as well as to the `entryLocalService` provided by the contacts api.

```
package web.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

@Component(
	immediate = true,
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/*"
	},
	service = Servlet.class
)
public class SampleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter writer = response.getWriter();

		String fullNameParameter = ParamUtil.getString(request, "fullName");

		if (Validator.isNull(fullNameParameter)) {
			writer.println("<h2>Hello You!</h2>");
			writer.println("Do you want to sign up for this thing?<br/>");
			writer.println("<form action='/join' method='post'>");
			writer.println("<input type='text' name='fullName' placeholder='Full Name'><br>");
			writer.println("<input type='text' name='emailAddress' placeholder='Email Address'><br>");
			writer.println("<input type='submit' value='Sign Up'><br>");
			writer.println("</form>");

			List<Entry> entries = entryLocalService.getEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			if (entries.isEmpty()) {
				writer.println("I'm so lonely! :(<br/>");
			}
			else {
				writer.println("Here's a list of others who've already signed up:<br/>");

				for (Entry entry : entryLocalService.getEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
					writer.println(String.format("%s &lt;%s><br/>", entry.getFullName(), entry.getEmailAddress()));
				}
			}

			return;
		}

		String emailAddressParameter = ParamUtil.getString(request, "emailAddress");

		if (Validator.isNull(emailAddressParameter)) {
			writer.println(String.format("Ooops! %s, you forgot your emailAddress :(<br/>", fullNameParameter));
			writer.println("<a href='/'>Retry?</a>");

			return;
		}

		DynamicQuery dynamicQuery = entryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddress", emailAddressParameter));

		long count = entryLocalService.dynamicQueryCount(dynamicQuery);

		if (count > 0) {
			writer.println(String.format("Ooops! Someone already registered with the email address &lt;%s> :(<br/>", emailAddressParameter));
			writer.println("<a href='/'>Retry?</a>");

			return;
		}

		long entryId = counterLocalService.increment();

		Entry entry = entryLocalService.createEntry(entryId);

		entry.setFullName(fullNameParameter);
		entry.setEmailAddress(emailAddressParameter);

		entryLocalService.updateEntry(entry);

		writer.println(String.format("Great! Thanks for signing up %s :D<br/>", fullNameParameter));
		writer.println("<a href='/'>Go Back!</a>");
	}

	@Reference
	private CounterLocalService counterLocalService;
	@Reference
	private EntryLocalService entryLocalService;

}
```

