/* @generated */
package freemarker.debug;

import java.util.EventObject;

/**
 * Event describing a suspension of an environment (ie because it hit a
 * breakpoint).
 * @author Attila Szegedi
 * @version $Id: EnvironmentSuspendedEvent.java,v 1.1.2.1 2006/11/27 07:54:19 szegedia Exp $
 */
public class EnvironmentSuspendedEvent extends EventObject
{
    private static final long serialVersionUID = 1L;

    private final String templateName;
    private final long threadId;
    private final int line;
    private final DebuggedEnvironment env;

    public EnvironmentSuspendedEvent(Object source, String templateName, int line, DebuggedEnvironment env)
    {
        super(source);
        this.templateName = templateName;
        this.threadId = Thread.currentThread().getId();
        this.line = line;
        this.env = env;
    }

    /**
     * The name of the template where the execution of the environment
     * was suspended
     * @return String the template name
     */
    public String getTemplateName()
    {
        return this.templateName;
    }

    public long getThreadId()
    {
        return this.threadId;
    }

    /**
     * The line number in the template where the execution of the environment
     * was suspended.
     * @return int the line number
     */
    public int getLine()
    {
        return line;
    }

    /**
     * The environment that was suspended
     * @return DebuggedEnvironment
     */
    public DebuggedEnvironment getEnvironment()
    {
        return env;
    }
}
