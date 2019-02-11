package ${configYAML.apiPackagePath}.test.${versionDirName};

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.runner.RunWith;

/**
 * @author ${configYAML.author}
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ${schemaName}Test extends Base${schemaName}TestCase {
}