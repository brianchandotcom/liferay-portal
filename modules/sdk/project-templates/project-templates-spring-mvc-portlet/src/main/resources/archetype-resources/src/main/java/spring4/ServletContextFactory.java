package ${package}.spring4;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.FactoryBean;

import org.springframework.web.context.ServletContextAware;

/**
 * @author ${author}
 */
public class ServletContextFactory
	implements FactoryBean<ServletContext>, ServletContextAware {

	@Override
	public ServletContext getObject() throws Exception {
		return _servletContext;
	}

	@Override
	public Class<?> getObjectType() {
		return ServletContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;
}