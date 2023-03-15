@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4
Feature: Create a Segment with a Number Property
    As a Business User, I should be able to create a Segment with a Number Property

    Background: [Setup] Go to Dynamic Individuals Segment
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click "Segments" in the sidebar
        * I should see the "Segments" page
        * I click the "Create Segment" button
        * I click the "Dynamic Segment" dropdown option

    Scenario: Create Segment with Number Property
        Given I select "Account Attributes" from the criterion type dropdown
        When I create a criteria with the following conditions:
            | annualRevenue | less than | 10000000 |
        And I name the Dynamic segment "CreateDynamicNumberSegment - ${Random.1}" and save it
        When I go to the "Segments" page
        And I search for "CreateDynamicNumberSegment - ${Random.1}"
        Then I should see a "Segment" named "CreateDynamicNumberSegment - ${Random.1}" with "7" items
