package ${configYAML.apiPackagePath}.resource;

import javax.ws.rs.Path;

/**
 * @author ${configYAML.author}
 */
@Path("/${info.version}/${name?lower_case}")
public interface ${name}Resource {
}