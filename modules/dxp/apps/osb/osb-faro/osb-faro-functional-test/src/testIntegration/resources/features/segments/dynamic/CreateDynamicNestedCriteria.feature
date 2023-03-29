@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_3
Feature: Create a nested criteria
    As a Business User, I should be able to create a nested criteria

    Background: [Setup] Create two Criteria
      * I go to the "Home" page
      * I login as "test@faro.io:test"
      * I should see the "Sites" page
      * I go to the "Segments" page
      * I click the "Create Segment" button
      * I click the "Dynamic Segment" dropdown option
      * I select "Individual Attributes" from the criterion type dropdown
      * I create a criteria with the following conditions:
        | gender   | is |      female        |
        | worksFor | is | schneider and sons |

    Scenario: Create the Nested Criteria
      When I create a nested OR criteria with the following conditions:
        | city | is | angelobury |
      And I name the Dynamic segment "CreateDynamicNestedCriteria - ${Random.1}" and save it
      And I go to the "Segments" page
      And I search for "CreateDynamicNestedCriteria - ${Random.1}"
      Then I should see a "Segment" named "CreateDynamicNestedCriteria - ${Random.1}" with "8" items