@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4
Feature: Create a Web Behavior criteria at least/at most times
    As a Business User, I should be able to create a Web Behavior using at least/at most

    Background: [Setup] Navigate to Dynamic Segment Creation Page
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I go to the "Segments" page
        * I click the "Create Segment" button
        * I click the "Dynamic Segment" dropdown option

    Scenario: Create a Web Behavior using at least
        Given I select "Web Behaviors" from the criterion type dropdown
        When I create a criteria with the following Web Behavior conditions:
            | Viewed Blog | has | engage customized platforms | at least | 2 | ever |
        And I name the Dynamic segment "CreateDynamicWebBehaviorsAtLeastAtMost - ${Random.1}" and save it
        When I go to the "Segments" page
        And I search for "CreateDynamicWebBehaviorsAtLeastAtMost - ${Random.1}"
        Then I should see a "Segment" named "CreateDynamicWebBehaviorsAtLeastAtMost - ${Random.1}" with "9" items

    Scenario: Create a Web Behavior using at most
        Given I select "Web Behaviors" from the criterion type dropdown
        When I create a criteria with the following Web Behavior conditions:
            | Viewed Blog | has not | engage customized platforms | at most | 2 | ever |
        And I name the Dynamic segment "CreateDynamicWebBehaviorsAtLeastAtMost - ${Random.1}" and save it
        And I go to the "Segments" page
        And I search for "CreateDynamicWebBehaviorsAtLeastAtMost - ${Random.1}"
        Then I should see a "Segment" named "CreateDynamicWebBehaviorsAtLeastAtMost - ${Random.1}" with "1" items