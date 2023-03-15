@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4
Feature: Create a Segment of Session in a Topic
    As a Business User, I should be able to create Session Property in a Segment

    Background: [Setup] Create a Dynamic Segment
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click "Segments" in the sidebar
        * I should see the "Segments" page
        * I click the "Create Segment" button
        * I click the "Dynamic Segment" dropdown option

    Scenario: Create the Session Property segment
        Given I select "Session Attributes" from the criterion type dropdown
        When I create a criteria with the following Session condition:
          | Browser | is | Firefox | Last 24 hours |
        And I name the Dynamic segment "CreateDynamicSessionSegment - ${Random.1}" and save it
        When I go to the "Segments" page
        And I search for "CreateDynamicSessionSegment - ${Random.1}"
        Then I should see a "Segment" named "CreateDynamicSessionSegment - ${Random.1}" with 92 items