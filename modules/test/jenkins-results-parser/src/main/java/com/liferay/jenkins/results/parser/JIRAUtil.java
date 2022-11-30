package com.liferay.jenkins.results.parser;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import io.atlassian.util.concurrent.Promise;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class JIRAUtil {
    public static void addIssue(String issueNumber) {
        Issue issue = _getIssue(issueNumber);


        if (_issueSet == null) {
            _issueSet = Collections.emptySet();
        }

        Set<Issue> issueSet = Collections.singleton(issue);
        issueSet.addAll(_issueSet);

        _issueSet = issueSet;
    }

    public static Set<Issue> getIssues() {
        if (_issueSet == null) {
            _issueSet = Collections.emptySet();
        }

        if (_issueSet.isEmpty()) {
            throw new IllegalStateException("Unable to find any valid issues within pull request");
        }

        return _issueSet;
    }

    public static String generateComment(PullRequest pullRequest) {
        StringBuilder sb = new StringBuilder();

        sb.append("The following tickets were automatically submitted for review: \n");

        for (Issue issue : _issueSet) {
            String issueKey = issue.getKey();

            sb.append("https://issues.liferay.com/browse/");

            sb.append(issueKey);

            sb.append("\n");
        }

        sb.append("\n");

        sb.append("Pull request: ");

        String ownerUsername = pullRequest.getOwnerUsername();
        String gitHubRemoteGitRepositoryName = pullRequest.getGitHubRemoteGitRepositoryName();
        String number = pullRequest.getNumber();

        String url = pullRequest.getURL(ownerUsername, gitHubRemoteGitRepositoryName, number);

        sb.append(url);

        return sb.toString();
    }

    private static int _getTransitions(Issue issue, String transitionName) {
            if (_issueRestClient == null) {
                initRestClient();
            }

            if (_transitions == null) {
                Promise<Iterable<Transition>> promise = _issueRestClient.getTransitions(issue);

                _transitions = promise.claim();
            }

            int transitionId = -1;

            for (Iterator iterator = _transitions.iterator(); iterator.hasNext();) {

                Transition transition = (Transition) iterator.next();

                if(transition.getName().equals(transitionName)){
                    transitionId = transition.getId();
                }
            }

            return transitionId;
    }

    public static void transition(String comment, Issue issue, int transitionId) {
        if (_issueRestClient == null) {
            initRestClient();
        }

        TransitionInput transitionInput = new TransitionInput(transitionId, Comment.valueOf(comment));

        try {
            Promise<Void> transition = _issueRestClient.transition(issue, transitionInput);

            transition.get();

        } catch (Exception exception) {
            System.out.println("JIRA rest client process workflow action error. cause: " + exception.getMessage());
        }
    }

    public static void transition(String comment, Issue issue, String transitionName) {
        if (_issueRestClient == null) {
            initRestClient();
        }

        int transitionId = _getTransitions(issue, transitionName);

        if (transitionId == -1) {
            // error out
        }

        TransitionInput transitionInput = new TransitionInput(transitionId, Comment.valueOf(comment));

        try {
            Promise<Void> transition = _issueRestClient.transition(issue, transitionInput);

            transition.get();

        } catch (Exception exception) {
            System.out.println("JIRA rest client process workflow action error. cause: " + exception.getMessage());
        }
    }

    private static Issue _getIssue(String issueNumber) {

        if (_issueRestClient == null) {
            initRestClient();
        }

        Promise<Issue> promise = _issueRestClient.getIssue(issueNumber);

        return promise.claim();
    }

    private static void initRestClient() {
        _getProperties();

        _jiraRestClientFactory = new AsynchronousJiraRestClientFactory();

        _jiraRestClient =
                _jiraRestClientFactory.createWithBasicHttpAuthentication(
                        _URI, Auth.JIRA_USERNAME,
                        Auth.JIRA_PASSWORD);

        _issueRestClient = _jiraRestClient.getIssueClient();
    }

    private static void _getProperties() {

        try {
            _jenkinsBuildProperties =
                    JenkinsResultsParserUtil.getBuildProperties();
        }
        catch (IOException ioException) {
            throw new RuntimeException(
                    "Unable to get build properties", ioException);
        }

        _jiraAdminPassword = _jenkinsBuildProperties.getProperty("ci.jira.admin.password");
        _jiraAdminUsername = _jenkinsBuildProperties.getProperty("ci.jira.admin.username");
    }

    private static IssueRestClient _issueRestClient;
    private static Properties _jenkinsBuildProperties;

    private static String _jiraAdminPassword;

    private static String _jiraAdminUsername;

    private static JiraRestClient _jiraRestClient;

    private static JiraRestClientFactory _jiraRestClientFactory;

    private static final URI _URI = URI.create("https://issues.liferay.com");

    private static Set<Issue> _issueSet;

    private static Iterable _transitions;
}
