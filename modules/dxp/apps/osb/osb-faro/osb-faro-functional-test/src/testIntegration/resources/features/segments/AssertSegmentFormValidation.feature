@spira_Segments @Segments @Creation @team_FARO @priority_5
Feature: Segment Form Validation
	As an Business User, I should be not able to save a segment if: the segment name is missing, there are no criteria,
	or if no entities are selected

	Background: [Setup] Navigate to Create Individuals Segment page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button

	Scenario: Empty Segment Name Field
		Given I click the "Static Segment" dropdown option
		Then I should not be able to click the "Create" button

	Scenario: No Criteria
		Given I click the "Dynamic Segment" dropdown option
		When I name the segment "MissingSegmentName ${Random.1}"
		Then I should not be able to click the "Save Segment" button

	Scenario: No Entities Selected
		Given I click the "Static Segment" dropdown option
		When I type "MissingSegmentName ${Random.1}" into the "Segment Name" input field
		Then I should not be able to click the "Create" button