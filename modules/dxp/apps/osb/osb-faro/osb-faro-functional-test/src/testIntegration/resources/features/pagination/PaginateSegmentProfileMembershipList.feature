@spira_Pagination @Pagination @Segments @List @Profile @team_FARO @priority_3
Feature: Paginate Segment Profile in Membership List
	As a Business User, I should be able to paginate to second page in membership list

	Background: [Setup] Navigate to Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Paginate to second page of Membership list
		Given I click the "Membership" tab
		When I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see an individual named "Paige DuBuque" in the table